package ru.sberbank.edu.ticketservice.profile.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.sberbank.edu.ticketservice.profile.dto.ProfileDto;
import ru.sberbank.edu.ticketservice.profile.service.ProfileService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/profile/edit")
    public String editUser(Principal principal, Model model) {
        ProfileDto user = service.getActiveUser(principal.getName());
        model.addAttribute("user", user);
        logger.info("editing user list: {}", user);
        return "edit-profile.html";
    }


    @PatchMapping("profile/update/{id}")
    public String updateUser(@PathVariable("id") String id, @Valid ProfileDto user,
                             BindingResult result, Model model) {


        if (result.hasErrors()) {
            user.setId(id);
            return "profileEdit";
        }

        service.update(user);
        return "redirect:/profile";
    }
}
