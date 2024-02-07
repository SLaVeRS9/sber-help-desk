package ru.sberbank.edu.ticketservice.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final ShortViewTicketMapper shortViewTicketMapper;

    @Transactional(readOnly = true)
    public List<ShortViewTicketDto> getAllShortViewTickets() {
        List<Ticket> tikets = ticketRepository.findAll();
        List<ShortViewTicketDto> shortViewTicketDtos = tikets.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto).toList();
        return shortViewTicketDtos;
    }
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
        //TODO ЗАглушка, поменять когда будет норм реализацция получения на фронте инфы из профиль пользователя
        fullViewTicketDto.getRequester().setId(UUID.randomUUID().toString());
        var ticket = fullViewTicketMapper.fullViewTicketDtoToTicket(fullViewTicketDto);
        ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

}
