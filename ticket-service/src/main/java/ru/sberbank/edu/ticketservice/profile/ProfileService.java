package ru.sberbank.edu.ticketservice.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sberbank.edu.ticketservice.entity.User;
import ru.sberbank.edu.ticketservice.enums.UserRole;
import ru.sberbank.edu.ticketservice.repository.UserRepository;
import ru.sberbank.edu.ticketservice.security.AppUserPrincipal;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
//    private final AppUserPrincipal userPrincipal;

    public User getActiveUser() {
//        User user = userRepository.findUserByName(userPrincipal.getUsername());
        User user = new User("ABraham", "John", "", UserRole.MANAGER);
        return user;
    }
//    }

}
