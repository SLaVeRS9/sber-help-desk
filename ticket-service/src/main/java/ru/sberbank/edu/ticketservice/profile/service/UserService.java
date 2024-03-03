package ru.sberbank.edu.ticketservice.profile.service;

import ru.sberbank.edu.common.error.exception.UserAlreadyExistsException;
import ru.sberbank.edu.common.error.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.repository.UserRepository;
import ru.sberbank.edu.ticketservice.profile.dto.UserDto;
import ru.sberbank.edu.ticketservice.profile.enums.UserRole;

import java.util.List;
import java.util.Optional;

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

//    public User getUserById(String id){
//        return userRepository.findUserById(id);
//    }
    
    public boolean loginExists(String login) {
        return userRepository.findById(login).isPresent();
    }
    
    public User getUserById(String id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException("User not found, id = " + id);
        }
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }
    
    public void updateRoles(List<User> users) {
       for(User user : users) {
           User savedUser = getUserById(user.getId());
           savedUser.setRole(user.getRole());
           userRepository.save(savedUser);
       }
    }
    
    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
}
