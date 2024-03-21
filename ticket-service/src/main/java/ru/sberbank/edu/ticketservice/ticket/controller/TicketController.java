package ru.sberbank.edu.ticketservice.ticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.service.UserService;
import ru.sberbank.edu.ticketservice.ticket.dto.EditTicketDto;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.dto.CreateTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.CreateTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.EditTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.service.TicketService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = TicketController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name="Tickets", description="Manage tickets")
public class TicketController {
    public static final String REST_URL = "/api/tickets";
    private final TicketService ticketService;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final ShortViewTicketMapper shortViewTicketMapper;
    private final CreateTicketMapper createTicketMapper;
    private final EditTicketMapper editTicketMapper;
    private final UserService userService;

    /**
     * Получить все тикеты в кратком содержании
     * @return список тикетов в кратком содержании
     */
    @GetMapping("/all/shortView")
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
    @GetMapping("/all/fullView")
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
    @GetMapping("/{userId}/page/shortView")
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

        return ticketService.getUserTicketsWithPagination(userId, offset, limit)
                .stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();
    }

    /**
     * Получить все тикеты пользователя в краткой форме
     * @param userId id пользователя
     * @return список тикетов пользователя в краткой форме
     */
    @GetMapping("/{userId}/ticket/shortView")
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
    @GetMapping("/{ticketId}/fullView")
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

    /**
     * Создать тикет
     * @param createTicketDto id тикета
     * @return тикет в полной форме
     */
    @PostMapping()
    @Operation(
            summary = "Create ticket",
            description = "You can create ticket and get created one"
    )
    public FullViewTicketDto createTicket(@RequestBody CreateTicketDto createTicketDto,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        var currentUserId = userDetails.getUsername();
        User requester = userService.getUserById(currentUserId);

        Ticket ticket = createTicketMapper.createTicketDtoToTicket(createTicketDto);
        ticket.setRequester(requester);
        ticket = ticketService.createTicket(ticket);

        return fullViewTicketMapper.ticketToFullViewTicketDto(ticket);
    }

    /**
     * Удалить тикет
     * @param id id тикета
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete ticket",
            description = "You can delete ticket"
    )
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }

    /**
     * Изменить данные в тикете
     * @param editTicketDto
     * @param editTicketDto
     * @return
     */
    @PatchMapping()
    public FullViewTicketDto editTicket(@RequestBody EditTicketDto editTicketDto) {
        Ticket edittedTicket = ticketService.getTicketInfo(editTicketDto.getId());
        edittedTicket = editTicketMapper.editTicketDtoToTicket(editTicketDto, edittedTicket);
        edittedTicket = ticketService.editTicket(edittedTicket);

        return fullViewTicketMapper.ticketToFullViewTicketDto(edittedTicket);
    }

    @PatchMapping("/{id}/assign-on-me")
    @Operation(
            summary = "Assign current manager on ticket",
            description = "You can assign tickets on current manager"
    )
    public FullViewTicketDto assignTicketOnManager(@PathVariable Long id) {

        Ticket assignedTicket = ticketService.assignManagerOnTicket(id);

        return fullViewTicketMapper.ticketToFullViewTicketDto(assignedTicket);
    }

    @GetMapping("/on-me")
    @Operation(
            summary = "Get tickets on manager",
            description = "You can get tickets assigned on manager"
    )
    public List<ShortViewTicketDto> getTicketsOnManager(@RequestParam String managerId) {
        List<Ticket> ticketsOnManager = ticketService.getTicketsOnManager(managerId);

        return ticketsOnManager.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();
    }

    @GetMapping("/unassigned-tickets")
    @Operation(
            summary = "Get tickets without manager",
            description = "You can get tickets unassigned on manager"
    )
    public List<ShortViewTicketDto> getUnassignedTickets() {
        List<Ticket> unassignedTickets = ticketService.getUnassignedTickets();

        return unassignedTickets.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();
    }


    /*@PatchMapping("/{id}/status")
    public FullViewTicketDto setStatus(@RequestBody String ticketStatus,
                                       @PathVariable("id") Long ticketId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ticketService.setStatus(ticketStatus, ticketId, userDetails);
    }*/

    /*@PatchMapping("/{id}/estimation")
    public FullViewTicketDto setStatus(@RequestBody String ticketStatus,
                                       @PathVariable("id") Long ticketId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ticketService.setStatus(ticketStatus, ticketId, userDetails);
    }*/


}

