package ru.sberbank.edu.ticketservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.edu.ticketservice.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

}
