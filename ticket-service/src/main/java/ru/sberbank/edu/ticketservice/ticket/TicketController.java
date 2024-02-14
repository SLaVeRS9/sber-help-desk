package ru.sberbank.edu.ticketservice.ticket;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public List<ShortViewTicketDto> getAllTicketsInShortView() {
        return ticketService.getAllTicketsInShortView();
    }

    @GetMapping("/{userId}")
    public List<FullViewTicketDto> getUserTicketsFullView(
            @PathVariable("userId") String userId,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {
        return ticketService.getUserTicketsFullView(userId, offset, limit);
    }
}
