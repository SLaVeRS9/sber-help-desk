package ru.sberbank.edu.ticketservice.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
