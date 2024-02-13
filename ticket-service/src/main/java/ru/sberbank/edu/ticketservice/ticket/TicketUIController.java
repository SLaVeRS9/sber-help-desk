package ru.sberbank.edu.ticketservice.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.profile.ProfileService;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;

import java.util.Set;

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
    private final ProfileService profileService;
    @GetMapping("/{id}")
    public String showTicketInfo(@PathVariable Long id, Model model,
                                 @AuthenticationPrincipal UserDetails currentUser) {
        var fullViewTicketDto = ticketService.getFullTicketInfo(id);
        model.addAttribute("ticket", fullViewTicketDto);
        model.addAttribute("userId", currentUser.getUsername());
        return "ticket-info";
    }

    //TODO подумать как проставить ограничения по ролям
    //TODO подумать над переделать под dto под редактирование
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id, Model model) {
        //TODO Обработка exception
        var fullViewTicketDto = ticketService.getFullTicketInfo(id);
        model.addAttribute("ticket", fullViewTicketDto);
        return "edit-ticket";
    }

    @PatchMapping("/{id}")
    public String editTicket(@ModelAttribute("ticket") FullViewTicketDto fullViewTicketDtoModel) {
        //TODO Обработка exception
        fullViewTicketDtoModel = ticketService.editTicket(fullViewTicketDtoModel);
        return "redirect:/tickets/"+fullViewTicketDtoModel.getId();
    }

    @GetMapping("/add")
    public String showAddPage(@ModelAttribute("ticket") FullViewTicketDto fullViewTicketDto,
                              //@ModelAttribute("managers") Set<UserDto> managers,
                              /*@AuthenticationPrincipal UserDetails currentUser*/
                              @ModelAttribute("currentUser") User currentUser) {
        fullViewTicketDto.setStatus(TicketStatus.NEW);
        //managers = profileService.getAllManagers;
        //String userId = currentUser.getUsername();
        //User user = profileService.getUserById();

        User user = profileService.getActiveUser();
        currentUser.setName(user.getName());
        currentUser.setId(user.getId());
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
