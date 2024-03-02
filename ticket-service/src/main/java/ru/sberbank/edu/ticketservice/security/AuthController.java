package ru.sberbank.edu.ticketservice.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import ru.sberbank.edu.ticketservice.profile.dto.UserDto;
import ru.sberbank.edu.ticketservice.profile.service.UserService;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
    
    private UserService userService;
    
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }
    
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult) {      
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        
        userService.registerNewUser(userDto);
        return "registration_success";
    }
    
}
