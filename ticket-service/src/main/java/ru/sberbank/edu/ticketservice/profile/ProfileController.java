package ru.sberbank.edu.ticketservice.profile;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor

public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService service;

    @GetMapping("/profile")

    public String listUsers(Model model) {
        User user = service.getActiveUser();
        model.addAttribute("user", user);
        logger.info("getting user list: {}", user);
        return "profile.html";
    }
}
