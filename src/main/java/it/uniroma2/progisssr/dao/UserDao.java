package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao  extends JpaRepository<User,String> {

    @Query("SELECT u.password FROM User u where u.username = :username")
    String findPasswordByUsername(@Param("username") String username);


}
