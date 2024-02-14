package ru.sberbank.edu.ticketservice.profile;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "users")
public class User {

    @Id
//    @Column (name = "user_id", unique = true)
    private String id;
    
    @Column (name = "name")
    private String name;
    
    @Column (name = "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column (name = "role")
    private UserRole role;

    @Column (name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Transient
    private int age;

    @Column (name = "date_of_register")
    private LocalDate dateOfRegister;

    @Enumerated(EnumType.STRING)
    @Column (name = "gender")
    private UserGender gender;


    public User() {
        
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears() + 1;
    }

    public User(String id, String name, String password, UserRole role) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
