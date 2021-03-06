package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserController {

    @Autowired
    UserDao userDao;
    @Autowired
    TicketDao ticketDao;

    @Transactional
    public @NotNull User createUser(@NotNull User user,@NotNull String username) {
        User newUser=null;
        if(!userDao.existsById(username))
            newUser= userDao.save(user);
        return newUser;
    }
    @Transactional
    public User updateUser(String username, User user) throws NotFoundEntityException {
        User userToUpdate = userDao.getOne(username);
        if (userToUpdate == null)
            throw new NotFoundEntityException();
        userToUpdate.update(user);
        User userUpdated = userDao.save(userToUpdate);
        return userUpdated;
    }



    public User findUserById(@NotNull String username) throws NotFoundEntityException {
        User user;
        if(!userDao.existsById(username))
            throw new NotFoundEntityException();
        user = userDao.getOne(username);
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
        return user.verifyPassword(userDao.findPasswordByUsername(username));
    }

}
