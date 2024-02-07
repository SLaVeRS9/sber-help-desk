package ru.sberbank.edu.ticketservice.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;

/**
 * Controller для обработки запросов на отображение ui
 * @author SLaVeRS9
 * @version 1.0
 */
//TODO прикрутить валидацию
//TODO добавить сортировку и плагинацию в дашборд
@Controller
//TODO вынести пути в проперти
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketUIController {
    private final TicketService ticketService;
    @GetMapping("/{id}")
    public String showTicketInfo(@PathVariable Long id, Model model) {
        var fullViewTicketDto = ticketService.getFullTicketInfo(id);
        model.addAttribute("ticket", fullViewTicketDto);
        return "ticket-info";
    }

    //TODO подумать над переделать под dto под редактирование
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id, Model model) {
        //TODO Обработка exception
        var fullViewTicketDto = ticketService.editTicket(id);
        model.addAttribute("ticket", fullViewTicketDto);
        return "edit-ticket";
    }

    //TODO Переписать метод, вся логика внутри не верная
    @PatchMapping("/{id}")
    public String editTicket(@PathVariable Long id, Model model) {
        //TODO Обработка exception
        var fullViewTicketDto = ticketService.editTicket(id);
        model.addAttribute("ticket", fullViewTicketDto);
        return "redirect:{id}";
    }

    @GetMapping("/add")
    public String showAddPage(@ModelAttribute("ticket") FullViewTicketDto fullViewTicketDto) {
        fullViewTicketDto.setStatus(TicketStatus.NEW);
        //TODO Реализовать вызов получения всех менеджеров и отправки их в вью
        return "add-ticket";
    }

    @PostMapping
    public String addTicket(@ModelAttribute("ticket") FullViewTicketDto fullViewTicketDto) {
        ticketService.addTicket(fullViewTicketDto);
        return "redirect:dashboard";
    }

    @DeleteMapping("/{id}")
    public String addTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/dashboard";
    }
}
