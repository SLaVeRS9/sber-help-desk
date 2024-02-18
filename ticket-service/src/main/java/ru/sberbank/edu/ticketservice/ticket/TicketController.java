package ru.sberbank.edu.ticketservice.ticket;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/tickets", produces = "application/json")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/shortViewList")
    public Set<ShortViewTicketDto> getAllTicketsInShortView() {
        return ticketService.getAllTicketsInShortView();
    }

    @GetMapping("/fullViewList")
    public Set<FullViewTicketDto> getAllTicketsFullView() {
        return ticketService.getAllTicketsInFullView();
    }

    @GetMapping("/user/pag/{userId}")
    public List<FullViewTicketDto> getUserTicketsWithPagination(
            @PathVariable("userId") String userId,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "1") Integer limit) {
        return ticketService.getUserTicketsFullView(userId, offset, limit);
    }

    @GetMapping("/user/{userId}")
    public List<FullViewTicketDto> getUserTickets(@PathVariable("userId") String userId) {
        return ticketService.getUserTicketsFullView(userId);
    }

    @GetMapping("/{ticketId}")
    public FullViewTicketDto getTicketById(@PathVariable("ticketId") Long ticketId) {
        return ticketService.getFullTicketInfo(ticketId);
    }

    @PatchMapping()
    public FullViewTicketDto editTicket(@RequestBody FullViewTicketDto fullViewTicketDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        return ticketService.editTicket(fullViewTicketDto, userId);
    }

    /**
     *
     * @param ticketId
     * @param objectNode - userId and managerId in json params
     * @return updated ticket
     */
    @PatchMapping("/{id}/edit/manager")
    public FullViewTicketDto setManager(@PathVariable("id") Long ticketId,
                                        @RequestBody ObjectNode objectNode) {
        return ticketService.setManager(ticketId, objectNode);
    }

    @PatchMapping("/{id}/edit/status")
    public FullViewTicketDto setStatus(@RequestBody String ticketStatus,
                                       @PathVariable("id") Long ticketId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ticketService.setStatus(ticketStatus, ticketId, userDetails);
    }

    @PostMapping()
    public FullViewTicketDto addTicket(@RequestBody FullViewTicketDto fullViewTicketDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return ticketService.addTicket(fullViewTicketDto, userDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        ticketService.deleteTicket(id, userDetails);
        return "redirect:/dashboard";
    }
}
