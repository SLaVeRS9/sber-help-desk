package ru.sberbank.edu.ticketservice.tasktracking.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.edu.ticketservice.entity.Ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Для управления областью "доски" с задачами
 * TODO переписать логирование через AOP
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardUIController {
    @GetMapping
    public String showDashboard(@ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model) {
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setCommentary("Comment1");
        ticket1.setCreationTime(LocalDateTime.now());
        tickets.add(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setCommentary("Comment2");
        ticket2.setCreationTime(LocalDateTime.now());
        tickets.add(ticket2);

        Ticket ticket3 = new Ticket();
        ticket3.setId(3L);
        ticket3.setCommentary("Comment3");
        ticket3.setCreationTime(LocalDateTime.now());
        tickets.add(ticket3);

        System.out.println(tickets);

        model.addAttribute("tickets", tickets);
        return "dashboard";
    }
}
