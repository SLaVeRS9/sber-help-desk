package ru.sberbank.edu.ticketservice.service;

import ru.sberbank.edu.ticketservice.entity.User;
import ru.sberbank.edu.ticketservice.enums.UserRole;
import ru.sberbank.edu.ticketservice.error.UserAlreadyExistsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.sberbank.edu.ticketservice.dto.UserDto;
import ru.sberbank.edu.ticketservice.repository.UserRepository;

@Service
public class UserService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User registerNewUser(final UserDto userDto) {
        if (loginExists(userDto.getLogin())) {
            throw new UserAlreadyExistsException("There is an account with that login: " + userDto.getLogin());
        }
        final User user = new User();
        user.setId(userDto.getLogin());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }
    
    public boolean loginExists(String login) {
        return userRepository.findById(login).isPresent();
    }
}
