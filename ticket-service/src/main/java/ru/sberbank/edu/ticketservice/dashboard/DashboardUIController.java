package ru.sberbank.edu.ticketservice.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.edu.ticketservice.ticket.TicketController;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

import java.util.List;

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
    public String showDashboard(Model model) {
        var shortViewTicketDtos = ticketController.getAllTickets();
        model.addAttribute("tickets", shortViewTicketDtos);
        return "dashboard";
    }
}
