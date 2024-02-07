package ru.sberbank.edu.ticketservice.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
