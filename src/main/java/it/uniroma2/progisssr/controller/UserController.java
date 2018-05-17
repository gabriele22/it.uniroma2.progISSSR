package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Product;
import it.uniroma2.progisssr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
@Service
public class UserController {

    @Autowired
    UserDao userDao;

    @Transactional
    public @NotNull User createUser(@NotNull User user) {

        User newUser= userDao.save(user);
        return newUser;
    }
}
