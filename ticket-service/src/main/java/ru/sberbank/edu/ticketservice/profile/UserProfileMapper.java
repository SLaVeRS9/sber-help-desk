package ru.sberbank.edu.ticketservice.profile;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * User to ProfileDto
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    ProfileDto UserToProfileDto(User user);
    User ProfileDtoToUser(ProfileDto profileDto);

    User ProfileDtoToUser(ProfileDto profileDto, @MappingTarget User user);
}
