package ru.sberbank.edu.ticketservice.ticket;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/tickets", produces = "application/json")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final ShortViewTicketMapper shortViewTicketMapper;

    @GetMapping("/shortView/all")
    public List<ShortViewTicketDto> getAllTicketsInShortView() {

        List<Ticket> ticketsList = ticketService.getAllTickets();

        return ticketsList.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/fullView/all")
    public Set<FullViewTicketDto> getAllTicketsFullView() {
        List<Ticket> ticketsList = ticketService.getAllTickets();

        return ticketsList.stream()
                .map(fullViewTicketMapper::ticketToFullViewTicketDto)
                .collect(Collectors.toSet());
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
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticketService.getTicketInfo(ticketId));
    }

    /*@PatchMapping()
    public FullViewTicketDto editTicket(@RequestBody FullViewTicketDto fullViewTicketDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        return ticketService.editTicket(fullViewTicketDto, userId);
    }*/


/**
     *
     * @param ticketId
     * @param objectNode - userId and managerId in json params
     * @return updated ticket
     */

    /*@PatchMapping("/{id}/edit/manager")
    public FullViewTicketDto setManager(@PathVariable("id") Long ticketId,
                                        @RequestBody ObjectNode objectNode) {
        return ticketService.setManager(ticketId, objectNode);
    }*/

    /*@PatchMapping("/{id}/edit/status")
    public FullViewTicketDto setStatus(@RequestBody String ticketStatus,
                                       @PathVariable("id") Long ticketId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ticketService.setStatus(ticketStatus, ticketId, userDetails);
    }*/

    /*@PostMapping()
    public FullViewTicketDto createTicket(@RequestBody CreateTicketDto createTicketDto,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        return ticketService.createTicket(createTicketDto, userDetails);
    }*/

    /*@DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        ticketService.deleteTicket(id, userDetails);
        return "redirect:/dashboard";
    }*/
}

