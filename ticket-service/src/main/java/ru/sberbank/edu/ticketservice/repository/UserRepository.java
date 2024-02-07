package ru.sberbank.edu.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.edu.ticketservice.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
