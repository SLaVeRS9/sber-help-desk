package ru.sberbank.edu.ticketservice.profile.dto;

import ru.sberbank.edu.ticketservice.profile.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRolesEditDto {
    
    private List<User> users;
    
    public UsersRolesEditDto() {
        this.users = new ArrayList<>();
    }
    
    public UsersRolesEditDto(List<User> users) {
        this.users = users;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public void addUser(User user) {
        this.users.add(user);
    }
}
