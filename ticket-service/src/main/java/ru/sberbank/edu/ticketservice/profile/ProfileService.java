package ru.sberbank.edu.ticketservice.profile;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.sberbank.edu.ticketservice.security.details.AppUserPrincipal;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;

    public ProfileDto getActiveUser(String name) {
        User user = userRepository.findUserById(name);
        return userProfileMapper.UserToProfileDto(user);
    }
    
    public User getActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
        return userRepository.findUserById(principal.getUsername());
    }

    public List<ProfileDto> getAllManagers() {
        List<User> users = userRepository.findAllUsersByRole(String.valueOf(UserRole.MANAGER));
        List<ProfileDto> profilesDto = users.stream()
                .map(userProfileMapper::UserToProfileDto).toList();
        return profilesDto;

    }


}
