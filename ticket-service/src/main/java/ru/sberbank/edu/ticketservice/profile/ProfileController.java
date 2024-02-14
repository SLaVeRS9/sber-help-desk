package ru.sberbank.edu.ticketservice.profile;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor

public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService service;

    @GetMapping("/profile")
    public String listUsers(Principal principal, Model model) {
        ProfileDto user = service.getActiveUser(principal.getName());
        model.addAttribute("user", user);
        logger.info("getting user list: {}", user);
        return "profile.html";
    }
    @GetMapping("/profileEdit")
    public String editUser(Principal principal, Model model) {
        ProfileDto user = service.getActiveUser(principal.getName());
        model.addAttribute("user", user);
        logger.info("editing user list: {}", user);
        return "profile.html";
    }

    @PutMapping("/profileEdit")
    public String updateUser(Principal principal, Model model) {
        return "profile.html";
    }
}
