package ru.sberbank.edu.ticketservice.profile;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * User to ProfileDto
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    ProfileDto UserToProfileDto(User user);
    User ProfileDtoToUser(ProfileDto profileDto);
}
