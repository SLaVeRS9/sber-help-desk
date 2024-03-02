package ru.sberbank.edu.ticketservice.ticket.controller;

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
    public List<ShortViewTicketDto> getUserTicketsWithPagination(
            @PathVariable("userId") String userId,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "1") Integer limit) {

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
    public List<ShortViewTicketDto> getUserTickets(@PathVariable("userId") String userId) {
        List<Ticket> ticketList = ticketService.getUserTickets(userId);
        return ticketList.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();
    }

    /**
     * Получить информация по тикету в полной форме
     * @param ticketId id тикета
     * @return тикет в полной форме
     */
    @GetMapping("/fullView/{ticketId}")
    public FullViewTicketDto getTicketById(@PathVariable("ticketId") Long ticketId) {
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

