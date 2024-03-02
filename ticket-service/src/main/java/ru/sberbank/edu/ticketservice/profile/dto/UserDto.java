package ru.sberbank.edu.ticketservice.profile.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sberbank.edu.ticketservice.security.validation.PasswordMatches;
import ru.sberbank.edu.ticketservice.security.validation.UniqueLogin;

@PasswordMatches(first = "password", second = "matchingPassword")
public class UserDto {
    @UniqueLogin
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20)
    private String login;
    
    @NotNull
    @NotEmpty
    private String name;
    
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 50)
    private String password;
    
    private String matchingPassword;
    
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
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
    public String getMatchingPassword() {
        return matchingPassword;
    }
    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
