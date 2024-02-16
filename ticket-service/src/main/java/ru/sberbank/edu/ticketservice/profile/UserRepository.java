package ru.sberbank.edu.ticketservice.profile;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sberbank.edu.ticketservice.profile.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    User findUserByName(String name);

    List<User> findByRole(UserRole role);

    User findUserById(String Id);
//    @Query ( "select u from User u join fetch u.role r where r = :rolename" )
    @Query("SELECT u FROM User u WHERE UPPER(u.role) = UPPER(:role)")
    List<User> findAllUsersByRole(@Param("role") String role);

}
