package ru.sberbank.edu.ticketservice.ticket;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.edu.common.error.EditTicketException;
import ru.sberbank.edu.common.error.TicketNotFoundException;
import ru.sberbank.edu.ticketservice.profile.*;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final ShortViewTicketMapper shortViewTicketMapper;
    private final UserService userService;

    @Value("${ticket.SLA.days}")
    private Long slaDays;
    @Value("${ticket.code}")
    private String ticketCode;
    private final static String TICKET_EDIT_EXCEPTION = "You can't edit ticket on that status";
    private final static String TICKET_NOT_FOUND_EXCEPTION = "Ticket not found, id = ";
    private final static String TICKET_EDIT_ALLOW_EXCEPTION= "You can't edit another's tickets";
    private final static String TICKET_DELETE_ALLOW_EXCEPTION= "You can't delete another's tickets";

    @Transactional(readOnly = true)
    public Set<FullViewTicketDto> getAllTicketsInFullView() {
        List<Ticket> tickets = ticketRepository.findAll();
        Set<FullViewTicketDto> fullViewTicketDtos = tickets.stream()
                .map(fullViewTicketMapper::ticketToFullViewTicketDto)
                .collect(Collectors.toSet());
        return fullViewTicketDtos;
    }

    @Transactional(readOnly = true)
    public Set<ShortViewTicketDto> getAllTicketsInShortView() {
        List<Ticket> tickets = ticketRepository.findAll();
        Set<ShortViewTicketDto> shortViewTicketDtos = tickets.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .collect(Collectors.toSet());
        return shortViewTicketDtos;
    }

    @Transactional(readOnly = true)
    public FullViewTicketDto getFullTicketInfo(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.getTicketById(id);

        if (optionalTicket.isEmpty()) {
            throw new TicketNotFoundException(TICKET_NOT_FOUND_EXCEPTION + id);
        }

        var ticket = optionalTicket.get();
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
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

    public FullViewTicketDto editTicket(FullViewTicketDto fullViewTicketDto, String userId) {
        UserRole userRole = userService.getUserById(userId).getRole();

        if(!isEditStatusAccess(fullViewTicketDto, userId)) {
            throw new EditTicketException(TICKET_EDIT_EXCEPTION);
        }

        Ticket ticket = fullViewTicketMapper.fullViewTicketDtoToTicket(fullViewTicketDto);
        String requesterId = ticket.getRequester().getId();
        String managerId = ticket.getManager().getId();
        ticket.setRequester(userService.getUserById(requesterId));
        ticket.setManager(userService.getUserById(managerId));

        switch (userRole) {
            case USER -> {
                if(fullViewTicketDto.getRequesterId().equals(userId)) {
                    ticketRepository.save(ticket);
                } else {
                    throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
                }
            }
            case MANAGER -> {
                if(fullViewTicketDto.getManagerId().equals(userId)) {
                    ticketRepository.save(ticket);
                } else {
                    throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
                }
            }
            case ADMIN -> {
                ticketRepository.save(ticket);
            }
        }
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
    }

    public boolean isEditStatusAccess(FullViewTicketDto fullViewTicketDto, String userId) {
        UserRole userRole = userService.getUserById(userId).getRole();

        if((fullViewTicketDto.getStatus().equals(TicketStatus.CLOSED) ||
                fullViewTicketDto.getStatus().equals(TicketStatus.ARCHIVED)) &&
                !userRole.equals(UserRole.ADMIN)) {
            return false;
        }
        return true;
    }

    private boolean isEditAllow(FullViewTicketDto fullViewTicketDto, String userId) {
        User currentUser = userService.getUserById(userId);

        if(!fullViewTicketDto.getRequesterId().equals(currentUser.getId())) {
            return false;
        }
        return true;
    }

    public FullViewTicketDto setManager(Long ticketId, ObjectNode objectNode) {
        String userId = objectNode.get("userId").asText();
        String managerId = objectNode.get("managerId").asText();
        FullViewTicketDto ticketDto = getFullTicketInfo(ticketId);
        if(!isEditStatusAccess(ticketDto, userId)) {
            throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
        }

        if(!ticketDto.getManagerId().isEmpty() &&
                !isEditAllow(ticketDto, userId)) {
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
    }

    private boolean isManagerId(String id) {
        User user = userService.getUserById(id);
        return user.getRole().equals(UserRole.MANAGER);
    }

    public FullViewTicketDto addTicket(FullViewTicketDto fullViewTicketDto, UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();
        fullViewTicketDto.setCode(ticketCode);
        fullViewTicketDto.setStatus(TicketStatus.NEW);
        fullViewTicketDto.setRequesterId(currentUserId);
        fullViewTicketDto.setControlPeriodAt(LocalDateTime.now().plusDays(slaDays));

        var ticket = fullViewTicketMapper.fullViewTicketDtoToTicket(fullViewTicketDto);
        ticket.setRequester(userService.getUserById(ticket.getRequester().getId()));
        ticket.setManager(userService.getUserById(ticket.getManager().getId()));
        Ticket addedTicket = ticketRepository.save(ticket);
        return fullViewTicketMapper.ticketToFullViewTicketDto(addedTicket);
    }

    public void deleteTicket(Long id, UserDetails userDetails) {
        User currentUser = userService.getUserById(userDetails.getUsername());
        FullViewTicketDto ticketDto = getFullTicketInfo(id);
        if ( !(currentUser.getRole().equals(UserRole.ADMIN) ||
                (ticketDto.getRequesterId().equals(currentUser.getId())))) {
            throw new EditTicketException(TICKET_DELETE_ALLOW_EXCEPTION);
        }
        ticketRepository.deleteById(id);
    }

    public void setStatus(Long ticketId, UserDetails userDetails, TicketStatus status) {
        User currentUser = userService.getUserById(userDetails.getUsername());
        FullViewTicketDto ticketDto = getFullTicketInfo(ticketId);

        if ( !(currentUser.getRole().equals(UserRole.ADMIN) ||
                ticketDto.getManagerId().equals(currentUser.getId()))) {
            throw new EditTicketException(TICKET_EDIT_ALLOW_EXCEPTION);
        }
    }

}
