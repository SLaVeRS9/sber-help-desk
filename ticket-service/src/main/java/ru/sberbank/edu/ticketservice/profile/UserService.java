package ru.sberbank.edu.ticketservice.profile;

import ru.sberbank.edu.common.error.UserAlreadyExistsException;
import ru.sberbank.edu.common.error.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
