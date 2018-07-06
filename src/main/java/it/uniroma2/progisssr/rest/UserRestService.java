package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.TicketController;
import it.uniroma2.progisssr.controller.UserController;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "user")
@CrossOrigin
public class UserRestService {

    @Autowired
    private UserController userController;
    @Autowired
    private TicketController ticketController;

    @RequestMapping(path = "{username}", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user, @PathVariable String username) {

        User newUser = userController.createUser(user,username);
        //return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        return new ResponseEntity<>(newUser, newUser == null ? HttpStatus.FOUND : HttpStatus.CREATED);

    }

    @RequestMapping(path = "{username}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
        User userUpdated = null;
        try {
            userUpdated = userController.updateUser(username, user);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(userUpdated, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }


    @RequestMapping(path = "{username}", method = RequestMethod.GET)
    public ResponseEntity<User> findUser(@PathVariable String username) throws NotFoundEntityException {
        User user = null;
        try {
            user = userController.findUserById(username);
        }catch (NotFoundEntityException e){
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
        }catch (NotFoundEntityException e){
            e.printStackTrace();
            return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
        }
        if(userToAuthenticated==null)
            return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
        boolean userIsAuthenticated = userController.userVerifyCredentials(username, user);
        if (userIsAuthenticated){
                return new ResponseEntity<>(userToAuthenticated,HttpStatus.OK);
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
