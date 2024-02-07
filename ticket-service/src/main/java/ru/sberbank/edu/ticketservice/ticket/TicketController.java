package ru.sberbank.edu.ticketservice.ticket;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public List<ShortViewTicketDto> getAllTickets() {
        return ticketService.getAllShortViewTickets();
    }
}
