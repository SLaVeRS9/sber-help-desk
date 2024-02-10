package ru.sberbank.edu.ticketservice.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public User() {
        
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
