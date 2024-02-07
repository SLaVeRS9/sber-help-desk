package ru.sberbank.edu.ticketservice.security.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.profile.UserRepository;
import ru.sberbank.edu.ticketservice.security.details.AppUserPrincipal;

@Service
public class AppUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        
        return new AppUserPrincipal(user.get());
    }
}
