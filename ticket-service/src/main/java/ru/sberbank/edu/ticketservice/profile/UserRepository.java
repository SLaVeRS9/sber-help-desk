package ru.sberbank.edu.ticketservice.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.edu.ticketservice.profile.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByName(String name);
}
