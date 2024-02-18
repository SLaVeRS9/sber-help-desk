package ru.sberbank.edu.ticketservice.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.ticket.TicketController;


/**
 * Для управления областью "доски" с задачами
 * TODO переписать логирование через AOP
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardUIController {
    private final TicketController ticketController;

    @GetMapping
    public String showDashboard(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        var shortViewTicketDtos = ticketController.getAllTicketsInShortView();
        model.addAttribute("tickets", shortViewTicketDtos);
        model.addAttribute("userId", currentUser.getUsername());
        return "dashboard";
    }
}
