package ru.sberbank.edu.ticketservice.ticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.dto.CreateTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.CreateTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.service.TicketService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/tickets", produces = "application/json")
@AllArgsConstructor
@Tag(name="Tickets", description="Manage tickets")
public class TicketController {
    private final TicketService ticketService;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final ShortViewTicketMapper shortViewTicketMapper;
    private final CreateTicketMapper createTicketMapper;

    /**
     * Получить все тикеты в кратком содержании
     * @return список тикетов в кратком содержании
     */
    @GetMapping("/shortView/all")
    @Operation(
            summary = "Get all tickets in short view",
            description = "You can get all tickets in short form"
    )
    public List<ShortViewTicketDto> getAllTicketsInShortView() {

        List<Ticket> ticketsList = ticketService.getAllTickets();

        return ticketsList.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .collect(Collectors.toList());
    }

    /**
     * Получить все тикеты в полном содержании
     * @return список тикетов в полном содержании
     */
    @GetMapping("/fullView/all")
    @Operation(
            summary = "Get all tickets in full view",
            description = "You can get all tickets in full form"
    )
    public List<FullViewTicketDto> getAllTicketsFullView() {
        List<Ticket> ticketsList = ticketService.getAllTickets();

        return ticketsList.stream()
                .map(fullViewTicketMapper::ticketToFullViewTicketDto)
                .toList();
    }

    /**
     * Получить все тикеты пользователя в краткой форме в определенном диапазоне
     * @param userId id пользователя
     * @param offset порядковый номер первого тикета от всех тикетов пользователя
     * @param limit порядковый номер последнего тикета от всех тикетов пользователя
     * @return список тикетов от offset до limit
     */
    @GetMapping("/shortView/{userId}/page")
    @Operation(
            summary = "Get user tickets from-to in short view",
            description = "You can get all user tickets in short form from-to"
    )
    public List<ShortViewTicketDto> getUserTicketsWithPagination(
            @PathVariable("userId")
            @Parameter(description = "User id")
            String userId,
            @RequestParam(required = false, defaultValue = "0")
            @Parameter(description = "Start position")
            Integer offset,
            @RequestParam(required = false, defaultValue = "1")
            @Parameter(description = "Count from start position")
            Integer limit) {

        return ticketService.getUserTicketsFullView(userId, offset, limit)
                .stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();
    }

    /**
     * Получить все тикеты пользователя в краткой форме
     * @param userId id пользователя
     * @return список тикетов пользователя в краткой форме
     */
    @GetMapping("/shortView/{userId}")
    @Operation(
            summary = "Get user tickets in short view",
            description = "You can get all user tickets in short form"
    )
    public List<ShortViewTicketDto> getUserTickets(
            @PathVariable("userId")
            @Parameter(description = "User id")
            String userId) {
        List<Ticket> ticketList = ticketService.getUserTickets(userId);
        return ticketList.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();
    }

    /**
     * Получить информацию по тикету в полной форме
     * @param ticketId id тикета
     * @return тикет в полной форме
     */
    @GetMapping("/fullView/{ticketId}")
    @Operation(
            summary = "Get ticket info in full view",
            description = "You can get info about ticket in full view by his id"
    )
    public FullViewTicketDto getTicketById(
            @PathVariable("ticketId")
            @Parameter(description = "Ticket id")
            Long ticketId) {
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticketService.getTicketInfo(ticketId));
    }


    @PostMapping()
    public FullViewTicketDto createTicket(@RequestBody CreateTicketDto createTicketDto) {
        Ticket ticket = createTicketMapper.createTicketDtoToTicket(createTicketDto);
        ticket = ticketService.createTicket(ticket);
        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
    }






    /*@DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        ticketService.deleteTicket(id, userDetails);
        return "redirect:/dashboard";
    }*/





    /**
     * Изменить данные в тикете
     * @param createTicketDto
     * @param userDetails
     * @return
     */
    /*@PatchMapping()
    public FullViewTicketDto editTicket(@RequestBody EditTicketDto editTicketDto) {
        ticketService.getTicketInfo(createTicketDto.)
        Ticket ticket = createTicketMapper.createTicketDtoToTicket(createTicketDto)
        return ticketService.editTicket(fullViewTicketDto);
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


}

