package ru.sberbank.edu.ticketservice.profile;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.sberbank.edu.ticketservice.profile.dto.ProfileDto;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import org.mapstruct.factory.Mappers;

/**
 * User to ProfileDto
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    ProfileDto UserToProfileDto(User user);
    User ProfileDtoToUser(ProfileDto profileDto);

    User ProfileDtoToUser(ProfileDto profileDto, @MappingTarget User user);
}
