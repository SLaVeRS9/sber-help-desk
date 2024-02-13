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
        User user = new User("smirnovav", "Smirnov Alexey", "$2a$12$HHSybys2IZOGkIev4I0nuOYQkNvtjVamreLkz8iRazn0jHABlrsT6", UserRole.MANAGER);
        return user;
    }
//    }

}
