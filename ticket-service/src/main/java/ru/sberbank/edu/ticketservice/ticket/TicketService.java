package ru.sberbank.edu.ticketservice.ticket;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.edu.common.AuthenticationFacade;
import ru.sberbank.edu.common.error.EditTicketException;
import ru.sberbank.edu.common.error.TicketNotFoundException;
import ru.sberbank.edu.ticketservice.profile.*;
import ru.sberbank.edu.ticketservice.ticket.dto.CreateTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.CreateTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;

import java.util.List;

import static org.hibernate.Hibernate.unproxy;

//TODO Проверку что текущий менеджер имеет роль менеджер и если уже не имеет, то удалить его с тикета
//TODO Проверку что текущий менеджер есть в базе и если уже не имеет, то удалить его с тикета
@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final ShortViewTicketMapper shortViewTicketMapper;
    private final UserService userService;
    private final CreateTicketMapper createTicketMapper;
    private final AuthenticationFacade authenticationFacade;

    @Value("${ticket.SLA.days}")
    private Long slaDays;
    @Value("${ticket.code}")
    private String ticketCode;
    @Value("${ticket.startedStatus}")
    private String startedStatus;

    private final static String TICKET_EDIT_EXCEPTION = "You can't edit ticket on that status";
    private final static String TICKET_NOT_FOUND_EXCEPTION = "Ticket not found, id = ";
    private final static String TICKET_EDIT_ALLOW_EXCEPTION= "You can't edit another's tickets";
    private final static String TICKET_DELETE_ALLOW_EXCEPTION= "You can't delete another's tickets";
    private final static String TICKET_CREATE_ALLOW_EXCEPTION= "You can't create another's tickets";

    /**
     * Метод получения всех тикетов
     * @return список тикетов
     */
    @Transactional(readOnly = true)
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Метод получения тикета по его id
     * @param id id тикета
     * @return - тикет
     */
    @Transactional(readOnly = true)
    public Ticket getTicketInfo(Long id) {
        Ticket ticket = ticketRepository.getTicketById(id)
                .orElseThrow(() -> new TicketNotFoundException(TICKET_NOT_FOUND_EXCEPTION + id));

        return ticket;
    }

    @Transactional(readOnly = true)
    public List<FullViewTicketDto> getUserTicketsFullView(String userId, Integer offset, Integer limit) {
        List<Ticket> tickets = ticketRepository.getTicketsByRequesterId(userId, PageRequest.of(offset, limit));
        List<FullViewTicketDto> fullViewTicketDtos = tickets.stream()
                .map(fullViewTicketMapper::ticketToFullViewTicketDto).toList();
        return fullViewTicketDtos;
    }

    @Transactional(readOnly = true)
    public List<FullViewTicketDto> getUserTicketsFullView(String userId) {
        List<Ticket> tickets = ticketRepository.getTicketsByRequesterId(userId);
        List<FullViewTicketDto> fullViewTicketDtos = tickets.stream()
                .map(fullViewTicketMapper::ticketToFullViewTicketDto).toList();
        return fullViewTicketDtos;
    }


    public Ticket editTicket(Ticket ticket) {

        User requester = Hibernate.unproxy(ticket.getRequester(), User.class);

        ticket.setRequester(requester);

        if( !isCanEditTicketInfo(ticket)) {
            throw new EditTicketException(TICKET_EDIT_EXCEPTION);
        }



        ticketRepository.save(ticket);

        return ticket;
    }





    /*public FullViewTicketDto setManager(Long ticketId, ObjectNode objectNode) {
        String userId = objectNode.get("userId").asText();
        String managerId = objectNode.get("managerId").asText();
        FullViewTicketDto ticketDto = getTicketInfo(ticketId);
        if(isEditStatusClose(ticketDto, userId)) {
            throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
        }

        if(!ticketDto.getManagerId().isEmpty() &&
                !isOwnerTask(ticketDto, userId)) {
            throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
        }
        Ticket updatedTicket = fullViewTicketMapper.fullViewTicketDtoToTicket(ticketDto);

        if(!isManagerId(managerId)){
            throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
        }
        updatedTicket.setManager(userService.getUserById(managerId));
        updatedTicket.setRequester(userService.getUserById(userId));
        updatedTicket = ticketRepository.save(updatedTicket);
        return fullViewTicketMapper.ticketToFullViewTicketDto(updatedTicket);
    }*/



    /**
     * Сервис создания тикета
     * @param createTicketDto объект dto с данными для создания
     * @return созданный тикет
     */
    public Ticket createTicket(CreateTicketDto createTicketDto) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String currentUserId = authentication.getName();

        if( !(currentUserId.equals(createTicketDto.getRequesterId()) ||
                hasCurrentUserAdminRole())){
            //TODO Сделать свой
            throw new RuntimeException(TICKET_CREATE_ALLOW_EXCEPTION);
        }

        String requesterId = createTicketDto.getRequesterId();
        User requester = userService.getUserById(requesterId);

        String managerId = createTicketDto.getManagerId();
        User manager = userService.getUserById(managerId);

        Ticket ticket = createTicketMapper.createTicketDtoToTicket(createTicketDto);
        ticket.setRequester(requester);
        ticket.setManager(manager);
        ticket.setStatus(TicketStatus.valueOf(startedStatus));

        return ticketRepository.save(ticket);
    }

    /**
     * Сервис удаления тикета
     * @param id id тикета для удаления
     */
    public void deleteTicket(Long id) {
        Ticket ticket = getTicketInfo(id);

        if ( !(hasCurrentUserAdminRole() ||
                (isCurrentUserTicket(ticket)))) {
            throw new EditTicketException(TICKET_DELETE_ALLOW_EXCEPTION);
        }

        ticketRepository.deleteById(id);
    }

    /*public FullViewTicketDto setStatus(String status, Long ticketId, UserDetails userDetails) {
        User currentUser = userService.getUserById(userDetails.getUsername());
        FullViewTicketDto ticketDto = getTicketInfo(ticketId);

        if ( !(currentUser.getRole().equals(UserRole.ADMIN) ||
                ticketDto.getManagerId().equals(currentUser.getId()))) {
            throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
        }

        ticketDto.setStatus(TicketStatus.valueOf(status));
        Ticket ticket = fullViewTicketMapper.fullViewTicketDtoToTicket(ticketDto);
        mapperUsersToTicket(ticketDto, ticket);
        Ticket updatedTicket = ticketRepository.save(ticket);
        FullViewTicketDto updatedTicketDto = fullViewTicketMapper.ticketToFullViewTicketDto(updatedTicket);
        return updatedTicketDto;
    }*/

    /*private void mapperUsersToTicket(FullViewTicketDto ticketDto, Ticket ticket) {
        ticket.setRequester(userService.getUserById(ticketDto.getRequesterId()));
        ticket.setManager(userService.getUserById(ticketDto.getManagerId()));
    }*/



     boolean hasCurrentUserAdminRole() {
        Authentication authentication = authenticationFacade.getAuthentication();

        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + UserRole.ADMIN.name()));
    }
     boolean hasManagerRole() {
        Authentication authentication = authenticationFacade.getAuthentication();

        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + UserRole.MANAGER.name()));
    }

     private boolean isCurrentUserTicket(Ticket ticket) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String currentUserId = authentication.getName();
        return ticket.getRequester().getId().equals(currentUserId);
    }

    public boolean isCanEditTicketInfo(Ticket ticket) {
        System.out.println(isCurrentUserTicket(ticket));
        System.out.println(!(ticket.getStatus().equals(TicketStatus.CLOSED) || ticket.getStatus().equals(TicketStatus.ARCHIVED)));
        System.out.println(hasCurrentUserAdminRole());


         return (isCurrentUserTicket(ticket) &&
                !(ticket.getStatus().equals(TicketStatus.CLOSED) || ticket.getStatus().equals(TicketStatus.ARCHIVED))) ||
                hasCurrentUserAdminRole();
    }

}
