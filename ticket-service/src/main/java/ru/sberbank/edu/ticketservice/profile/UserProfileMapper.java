package ru.sberbank.edu.ticketservice.profile;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * User to ProfileDto
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    ProfileDto UserToProfileDto(User user);
    User ProfileDtoToUser(ProfileDto profileDto);
}
