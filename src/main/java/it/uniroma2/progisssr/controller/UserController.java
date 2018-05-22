package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserController {

    @Autowired
    UserDao userDao;
    @Autowired
    TicketDao ticketDao;

    @Transactional
    public @NotNull User createUser(@NotNull User user) {
        User newUser= userDao.save(user);
        return newUser;
    }

    @Transactional
    public User updateUser(String username, User user) throws EntitaNonTrovataException {
        User userToUpdate = userDao.getOne(username);
        if (userToUpdate == null)
            throw new EntitaNonTrovataException();
        userToUpdate.update(user);
        User userUpdated = userDao.save(userToUpdate);
        return userUpdated;
    }



    public User findUserById(@NotNull String username) throws EntitaNonTrovataException {
        User user;
        try {
            user = userDao.getOne(username);
        }catch (Exception e){
            e.printStackTrace();
            throw new EntitaNonTrovataException();
        }
        return user;
    }

    public boolean deleteUser(String username) {
        if (!userDao.existsById(username)) {
            return false;
        }
        userDao.deleteById(username);
        return true;
    }

    public List<User> findAllUsers() {
        List<User> users = userDao.findAll();
        return users;
    }

    public boolean userVerifyCredentials(@NotNull String username, @NotNull User user) {
        String password = userDao.findPasswordById(username);
        if (user.verifyPassword(password)) {
            return true;
        } else return false;
    }
}
