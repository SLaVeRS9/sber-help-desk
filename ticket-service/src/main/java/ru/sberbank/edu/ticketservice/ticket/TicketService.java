package ru.sberbank.edu.ticketservice.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FullViewTicketMapper fullViewTicketMapper;

    @Transactional(readOnly = true)
    public FullViewTicketDto getFullTicketInfo(Long id) {
        Ticket ticket = ticketRepository.getTicketById(id);
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
    }

    @Transactional(readOnly = true)
    public FullViewTicketDto editTicket(Long id) {
        Ticket ticket = ticketRepository.getTicketById(id);
        if(ticket.getStatus().equals(TicketStatus.CLOSED) || ticket.getStatus().equals(TicketStatus.ARCHIVED))
            //TODO добавить кастомный эксепшн
            throw new RuntimeException();
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
    }

    public void addTicket(FullViewTicketDto fullViewTicketDto) {
        var ticket = fullViewTicketMapper.fullViewTicketDtoToTicket(fullViewTicketDto);
        ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

}
