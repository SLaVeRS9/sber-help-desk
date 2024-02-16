package ru.sberbank.edu.ticketservice.profile;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    
    User findUserByName(String name);
    List<User> findByRole(UserRole role);
}
