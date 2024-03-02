package ru.sberbank.edu.ticketservice.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.RequiredArgsConstructor;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.enums.UserRole;
import ru.sberbank.edu.ticketservice.profile.service.UserService;
import ru.sberbank.edu.ticketservice.profile.dto.UsersRolesEditDto;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    
    private final UserService userService;
    
    @GetMapping(value = "/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "view-users";
    }
    
    @GetMapping(value = "/users/edit")
    public String editUsers(Model model) {
        List<User> users = new ArrayList<>();
        userService.findAll().iterator().forEachRemaining(users::add);        
        model.addAttribute("form", new UsersRolesEditDto(users));
        model.addAttribute("roles", new ArrayList<>(EnumSet.allOf(UserRole.class)));
        return "edit-users";
    }
    
    @PostMapping(value = "/users/save")
    public String editUsers(@ModelAttribute UsersRolesEditDto form, Model model) {
        userService.updateRoles(form.getUsers());
        return "redirect:/admin/users";
    }
    
//remove later    
    @GetMapping(value = "/test")
    public ModelAndView test(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        User user = userService.getUserById(principal.getName());
        System.out.println("current user id = " + user.getId());
        
        List<User> managers = userService.findByRole(UserRole.MANAGER);
        for (User m : managers) {
            System.out.println(m);
        }

        
        return modelAndView;
    }
}
