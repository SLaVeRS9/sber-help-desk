package ru.sberbank.edu.ticketservice.ticket;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.profile.*;
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
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketUIController {
    private final TicketService ticketService;
    private final UserService userService;

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
    public String editTicket(@ModelAttribute("ticket") @Valid FullViewTicketDto fullViewTicketDtoModel,
                             BindingResult bindingResult) {
        //TODO Обработка exception
        if (bindingResult.hasErrors())
            return "edit-ticket";
        fullViewTicketDtoModel = ticketService.editTicket(fullViewTicketDtoModel);
        return "redirect:/tickets/"+fullViewTicketDtoModel.getId();
    }

    @GetMapping("/add")
    public String showAddPage(@ModelAttribute("ticket") FullViewTicketDto fullViewTicketDto,
                              //@ModelAttribute("managers") Set<UserDto> managers,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        fullViewTicketDto.setStatus(TicketStatus.NEW);
        String currentUserId = userDetails.getUsername();
        User currentUser = userService.getUserById(currentUserId);
        model.addAttribute("currentUser", currentUser);
        //managers = profileService.getAllManagers;
        //String userId = currentUser.getUsername();
        //User user = profileService.getUserById();
        //TODO Реализовать вызов получения всех менеджеров и отправки их в вью
        return "add-ticket";
    }

    @PostMapping
    public String addTicket(@ModelAttribute("ticket") @Valid FullViewTicketDto fullViewTicketDto,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        if(bindingResult.hasErrors()) {
            String currentUserId = userDetails.getUsername();
            User currentUser = userService.getUserById(currentUserId);
            model.addAttribute("currentUser", currentUser);
            return "add-ticket";
        }
        ticketService.addTicket(fullViewTicketDto, userDetails);
        return "redirect:dashboard";
    }

    @DeleteMapping("/{id}")
    public String addTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/dashboard";
    }
}
