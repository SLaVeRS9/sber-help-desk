package ru.sberbank.edu.ticketservice.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.service.TicketService;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final TicketService ticketService;
    List<Ticket> getTicketsToDashboard() {
        List<Ticket> ticketsList = ticketService.getAllTickets();
        return ticketsList.stream()
                .filter(t -> ! (t.getStatus().equals(TicketStatus.ARCHIVED)))
                .collect(Collectors.toList());
    }
}
