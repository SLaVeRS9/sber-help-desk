package ru.sberbank.edu.ticketservice.profile;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.sberbank.edu.ticketservice.profile.dto.ProfileDto;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.mapper.UserProfileMapper;
import ru.sberbank.edu.ticketservice.profile.enums.UserRole;
import ru.sberbank.edu.ticketservice.profile.repository.UserRepository;
import ru.sberbank.edu.common.error.UserNotFoundException;
import ru.sberbank.edu.ticketservice.security.details.AppUserPrincipal;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;

    public ProfileDto getActiveUser(String name) {
        User user = userRepository.findUserById(name);
        return userProfileMapper.INSTANCE.UserToProfileDto(user);
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
    public User getUserById(String id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException("User not found, id = " + id);
        }
    }

    public User update(ProfileDto user) {
        User savedUser = getUserById(user.getId());
        savedUser.setGender(user.getGender());
        savedUser.setName(user.getName());
        savedUser.setDateOfBirth(user.getDateOfBirth());

        return userRepository.save(savedUser);
    }


    public User save(ProfileDto user) {

        return userRepository.save(userProfileMapper.INSTANCE.ProfileDtoToUser(user));
    }


}
