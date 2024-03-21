package ru.sberbank.edu.ticketservice.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.enums.UserRole;
import ru.sberbank.edu.ticketservice.profile.service.UserService;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.controller.TicketController;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.ShortViewTicketMapper;

import java.util.List;


/**
 * Для управления областью "доски" с задачами
 * TODO переписать логирование через AOP
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardUIController {
    private final DashboardService dashboardService;
    private final UserService userService;
    private final ShortViewTicketMapper shortViewTicketMapper;
    private final TicketController ticketController;

    @GetMapping
    public String showDashboard(Model model, @AuthenticationPrincipal UserDetails currentUser) {

        List<Ticket> ticketList = dashboardService.getTicketsToDashboard();

        List<ShortViewTicketDto> shortViewTicketDtoList = ticketList.stream()
                .map(shortViewTicketMapper::ticketToShortViewTicketDto)
                .toList();

        User currentUserEntity = userService.getUserById(currentUser.getUsername());
        UserRole currentUserRole = currentUserEntity.getRole();

        model.addAttribute("shortViewTicketDtosForDash", shortViewTicketDtoList);
        model.addAttribute("userId", currentUser.getUsername());
        model.addAttribute("currentUserRole", currentUserRole.name());
        return "dashboard";
    }

    @GetMapping("/current")
    public String showMyTickets(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        String currentUserId = currentUser.getUsername();
        List<ShortViewTicketDto> currentUserTickets = ticketController.getUserTickets(currentUserId);

        model.addAttribute("shortViewTicketDtos", currentUserTickets);
        return "my-tikets";
    }

    @GetMapping("/on-me")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public String showTicketsOnMe(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        String currentUserId = currentUser.getUsername();
        List<ShortViewTicketDto> currentUserTickets = ticketController.getTicketsOnManager(currentUserId);

        model.addAttribute("shortViewTicketDtos", currentUserTickets);
        return "tickets-on-me";
    }

    @GetMapping("/unassigned-tickets")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public String showUnasignedTickets(Model model) {
        List<ShortViewTicketDto> currentUserTickets = ticketController.getUnassignedTickets();

        model.addAttribute("shortViewTicketDtos", currentUserTickets);
        return "unassigned-tickets";
    }
}
