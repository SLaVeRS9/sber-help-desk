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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller для обработки запросов на отображение ui
 * @author SLaVeRS9
 * @version 1.0
 */

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketUIController {
    private final TicketService ticketService;
    private final UserService userService;
    private final UserProfileMapper userProfileMapper;

    @GetMapping("/{id}")
    public String showTicketInfo(@PathVariable Long id, Model model,
                                 @AuthenticationPrincipal UserDetails currentUser) {
        var fullViewTicketDto = ticketService.getFullTicketInfo(id);
        String requesterId = fullViewTicketDto.getRequesterId();
        String managerId = fullViewTicketDto.getManagerId();
        model.addAttribute("ticket", fullViewTicketDto);
        model.addAttribute("requester", userService.getUserById(requesterId));
        model.addAttribute("manager", userService.getUserById(managerId));
        model.addAttribute("userId", currentUser.getUsername());
        return "ticket-info";
    }

    //TODO подумать как проставить ограничения по ролям
    //TODO подумать над переделать под dto под редактирование
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails currentUser,
                               Model model) {
        //TODO Обработка exception

        var fullViewTicketDto = ticketService.getFullTicketInfo(id);
        List<User> managers = userService.findByRole(UserRole.MANAGER);
        Set<ProfileDto> managersDto = managers.stream()
                .map(userProfileMapper::UserToProfileDto)
                .collect(Collectors.toSet());

        if(!ticketService.isEditStatusAccess(fullViewTicketDto, currentUser.getUsername())){
            return "not-allow";
        }

        User requester = userService.getUserById(fullViewTicketDto.getRequesterId());
        User manager = userService.getUserById(fullViewTicketDto.getManagerId());
        ProfileDto requesterDto = userProfileMapper.UserToProfileDto(requester);
        ProfileDto managerDto = userProfileMapper.UserToProfileDto(manager);
        model.addAttribute("ticket", fullViewTicketDto);
        model.addAttribute("requester", requesterDto);
        model.addAttribute("manager", managerDto);
        model.addAttribute("managers", managersDto);
        return "edit-ticket";
    }

    @PatchMapping("/{id}")
    public String editTicket(@ModelAttribute("ticket") @Valid FullViewTicketDto fullViewTicketDtoModel,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal UserDetails userDetails) {
        //TODO Обработка exception
        if (bindingResult.hasErrors())
            return "edit-ticket";
        String userId = userDetails.getUsername();
        fullViewTicketDtoModel = ticketService.editTicket(fullViewTicketDtoModel, userId);
        return "redirect:/tickets/"+fullViewTicketDtoModel.getId();
    }

    @GetMapping("/add")
    public String showAddPage(@ModelAttribute("ticket") FullViewTicketDto fullViewTicketDto,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        fullViewTicketDto.setStatus(TicketStatus.NEW);
        String currentUserId = userDetails.getUsername();
        User currentUser = userService.getUserById(currentUserId);
        List<User> managers = userService.findByRole(UserRole.MANAGER);
        Set<ProfileDto> managersDto = managers.stream()
                .map(userProfileMapper::UserToProfileDto)
                .collect(Collectors.toSet());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("managers", managersDto);
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
            model.addAttribute("currentUser", currentUser.getName());
            User manager = userService.getUserById(fullViewTicketDto.getManagerId());
            model.addAttribute("managerName", manager.getName());
            return "add-ticket";
        }
        ticketService.addTicket(fullViewTicketDto, userDetails);
        return "redirect:dashboard";
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        ticketService.deleteTicket(id, userDetails);
        return "redirect:/dashboard";
    }
}
