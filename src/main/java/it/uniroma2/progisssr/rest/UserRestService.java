package it.uniroma2.progisssr.rest;



import com.sun.org.apache.xpath.internal.operations.Bool;
import it.uniroma2.progisssr.controller.TicketController;
import it.uniroma2.progisssr.controller.UserController;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "user")
@CrossOrigin
public class UserRestService {

    @Autowired
    private UserController userController;
    @Autowired
    private TicketController ticketController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userController.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{username}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
        User userUpdated = null;
        try {
            userUpdated = userController.updateUser(username, user);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(userUpdated, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @RequestMapping(path = "{username}", method = RequestMethod.GET)
    public ResponseEntity<User> findUser(@PathVariable String username) throws EntitaNonTrovataException {
        User user = null;
        try {
            user = userController.findUserById(username);
        }catch (EntitaNonTrovataException e){
            e.printStackTrace();
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping (path = "login/{username}", method = RequestMethod.POST)
    public ResponseEntity<User> login (@PathVariable String username, @RequestBody User user) {
        User userToAuthenticated = null;
        try {
            userToAuthenticated =  userController.findUserById(username);
        }catch (EntitaNonTrovataException e){
            e.printStackTrace();
            return new ResponseEntity<>(userToAuthenticated,HttpStatus.NOT_FOUND);
        }
        if(userToAuthenticated==null)
            return new ResponseEntity<>(userToAuthenticated,HttpStatus.NOT_FOUND);
        boolean userIsAuthenticated = userController.userVerifyCredentials(username, user);
        if (userIsAuthenticated){
                return new ResponseEntity<>(user,HttpStatus.OK);
        }else
            return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @RequestMapping(path = "{username}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable String username) {
        boolean deleted = userController.deleteUser(username);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userController.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
