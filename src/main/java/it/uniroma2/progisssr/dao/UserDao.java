package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao  extends JpaRepository<User,Long> {
}
