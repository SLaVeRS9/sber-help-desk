package ru.sberbank.edu.ticketservice.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.profile.UserRole;
import ru.sberbank.edu.ticketservice.profile.UserService;
import ru.sberbank.edu.ticketservice.ticket.Ticket;
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

        model.addAttribute("fullViewTicketDtos", currentUserTickets);
        return "my-tikets";
    }
}
