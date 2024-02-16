package ru.sberbank.edu.ticketservice.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.edu.ticketservice.profile.*;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final ShortViewTicketMapper shortViewTicketMapper;
    private final ProfileService profileService;
    private final UserRepository userRepository;
    @Value("${ticket.SLA.days}")
    private Long slaDays;
    @Value("${ticket.code}")
    private String ticketCode;

    @Transactional(readOnly = true)
    public List<ShortViewTicketDto> getAllTicketsInShortView() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<ShortViewTicketDto> shortViewTicketDtos = tickets.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();
        return shortViewTicketDtos;
    }
    @Transactional(readOnly = true)
    public FullViewTicketDto getFullTicketInfo(Long id) {
        Ticket ticket = ticketRepository.getTicketById(id);
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
    }

    @Transactional(readOnly = true)
    public List<FullViewTicketDto> getUserTicketsFullView(String userId, Integer offset, Integer limit) {
        List<Ticket> tickets = ticketRepository.getTicketsByRequesterId(userId, PageRequest.of(offset, limit));
        List<FullViewTicketDto> fullViewTicketDtos = tickets.stream()
                .map(fullViewTicketMapper::ticketToFullViewTicketDto).toList();
        return fullViewTicketDtos;
    }

    public FullViewTicketDto editTicket(FullViewTicketDto fullViewTicketDto) {
        if(fullViewTicketDto.getStatus().equals(TicketStatus.CLOSED) || fullViewTicketDto.getStatus().equals(TicketStatus.ARCHIVED))
            //TODO добавить кастомный эксепшн
            throw new RuntimeException();
        Ticket ticket = fullViewTicketMapper.fullViewTicketDtoToTicket(fullViewTicketDto);
        Ticket oldTicket = ticketRepository.getTicketById(ticket.getId());
        ticket.setRequester(oldTicket.getRequester());
        ticket.setManager(oldTicket.getManager());
        ticket.setComments(oldTicket.getComments());
        ticketRepository.save(ticket);
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
    }

    public void addTicket(FullViewTicketDto fullViewTicketDto) {
        //TODO Заглушка, поменять когда будет норм реализацция получения на фронте инфы из профиль пользователя
        User currentUser = profileService.getActiveUser();
        fullViewTicketDto.setCode(ticketCode);
        fullViewTicketDto.setStatus(TicketStatus.NEW);
        fullViewTicketDto.setRequester(userRepository.getReferenceById(currentUser.getId()));
        fullViewTicketDto.setCreatedAt(LocalDateTime.now());
        fullViewTicketDto.setStatusUpdatedAt(LocalDateTime.now());
        fullViewTicketDto.setControlPeriodAt(LocalDateTime.now().plusDays(slaDays));

        var ticket = fullViewTicketMapper.fullViewTicketDtoToTicket(fullViewTicketDto);
        ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

}
