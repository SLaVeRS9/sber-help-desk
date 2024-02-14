package ru.sberbank.edu.ticketservice.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sberbank.edu.ticketservice.profile.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByName(String name);
    User findUserById(String Id);
//    @Query ( "select u from User u join fetch u.role r where r = :rolename" )
    @Query("SELECT u FROM User u WHERE UPPER(u.role) = UPPER(:role)")
    List<User> findAllUsersByRole(@Param("role") String role);


}
