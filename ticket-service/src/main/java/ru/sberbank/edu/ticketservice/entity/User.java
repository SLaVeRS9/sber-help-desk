package ru.sberbank.edu.ticketservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ru.sberbank.edu.ticketservice.enums.UserRole;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column (name = "id")
    private String id;
    
    @Column (name = "name")
    private String name;
    
    @Column (name = "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column (name = "role")
    private UserRole role;

}
