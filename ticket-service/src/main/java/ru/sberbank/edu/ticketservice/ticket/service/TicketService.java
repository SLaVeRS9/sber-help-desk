package ru.sberbank.edu.ticketservice.ticket.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.edu.common.AuthenticationFacade;
import ru.sberbank.edu.common.aspect.ToLog;
import ru.sberbank.edu.common.error.EditTicketException;
import ru.sberbank.edu.common.error.TicketNotFoundException;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.enums.UserRole;
import ru.sberbank.edu.ticketservice.profile.service.UserService;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.repository.TicketRepository;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;
import ru.sberbank.edu.ticketservice.ticket.mapper.CreateTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;

import java.util.ArrayList;
import java.util.List;


//TODO Проверку что текущий менеджер имеет роль менеджер и если уже не имеет, то удалить его с тикета
//TODO Проверку что текущий менеджер есть в базе и если уже не имеет, то удалить его с тикета
@Service
@Transactional
@RequiredArgsConstructor
@EnableCaching
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
    @Cacheable(value = "Tickets")
    @ToLog
    public List<Ticket> getAllTickets() {
        List<Ticket> ticketList = ticketRepository.findAll();
        System.out.println(ticketList);
        return ticketList;
    }

    /**
     * Метод получения тикета по его id
     * @param id id тикета
     * @return - тикет
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "Ticket", key = "#id")
    @ToLog
    public Ticket getTicketInfo(Long id) {
        Ticket ticket = ticketRepository.getTicketById(id)
                .orElseThrow(() -> new TicketNotFoundException(TICKET_NOT_FOUND_EXCEPTION + id));
        ticket.setComments(new ArrayList<>(ticket.getComments()));

        return ticket;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "TicketsPag", key = "#userId + #offset + #limit")
    @ToLog
    public List<Ticket> getUserTicketsFullView(String userId, Integer offset, Integer limit) {
        return ticketRepository.getTicketsByRequesterId(userId, PageRequest.of(offset, limit));
    }

    @Cacheable(value = "UserTickets", key = "#userId")
    @Transactional(readOnly = true)
    @ToLog
    public List<Ticket> getUserTickets(String userId) {
        return ticketRepository.getTicketsByRequesterId(userId);
    }

    /**
     * Сервис редактирования информации в тикете
     * @param ticket
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "Tickets", allEntries = true),
            @CacheEvict(value = "UserTickets", allEntries = true)
    })
    @CachePut(value = "Ticket", key = "#ticket.id")
    @ToLog
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
     * @param ticket новый тикет на создание
     * @return созданный тикет
     */
    @Caching(evict = {
            @CacheEvict(value = "Tickets", allEntries = true),
            @CacheEvict(value = "UserTickets", allEntries = true)
    })
    @CachePut(value = "Ticket", key = "#ticket.id")
    @ToLog
    public Ticket createTicket(Ticket ticket) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String currentUserId = authentication.getName();
        User requester = ticket.getRequester();
        String requesterId = requester.getId();

        if( !(currentUserId.equals(requesterId) ||
                hasCurrentUserAdminRole())){
            //TODO Сделать свой
            throw new RuntimeException(TICKET_CREATE_ALLOW_EXCEPTION);
        }


        /*User requester = userService.getUserById(requesterId);

        String managerId = ticket.getManagerId();
        User manager = userService.getUserById(managerId);*/

        /*Ticket ticket = createTicketMapper.createTicketDtoToTicket(createTicketDto);*/
        /*ticket.setRequester(requester);
        ticket.setManager(manager);*/
        ticket.setStatus(TicketStatus.valueOf(startedStatus));

        return ticketRepository.save(ticket);
    }

    /**
     * Сервис удаления тикета
     * @param id id тикета для удаления
     */
    @Caching(evict = {
            @CacheEvict(value = "Tickets", allEntries = true),
            @CacheEvict(value = "UserTickets", allEntries = true),
            @CacheEvict(value = "Ticket", key = "#id"),
    })
    @ToLog
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


    @ToLog
     public boolean hasCurrentUserAdminRole() {
        Authentication authentication = authenticationFacade.getAuthentication();

        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + UserRole.ADMIN.name()));
    }

    @ToLog
     boolean hasManagerRole() {
        Authentication authentication = authenticationFacade.getAuthentication();

        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + UserRole.MANAGER.name()));
    }

    @ToLog
     private boolean isCurrentUserTicket(Ticket ticket) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String currentUserId = authentication.getName();
        return ticket.getRequester().getId().equals(currentUserId);
    }

    @ToLog
    public boolean isCanEditTicketInfo(Ticket ticket) {
         return (isCurrentUserTicket(ticket) &&
                !(ticket.getStatus().equals(TicketStatus.CLOSED) || ticket.getStatus().equals(TicketStatus.ARCHIVED))) ||
                hasCurrentUserAdminRole();
    }

}
