package ru.sberbank.edu.ticketservice.profile.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sberbank.edu.ticketservice.profile.enums.UserGender;
import ru.sberbank.edu.ticketservice.profile.enums.UserRole;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ProfileDto {
    private String id;
    private String name;
    private UserRole role;
    private UserGender gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfRegister;
    private Integer age;
}
