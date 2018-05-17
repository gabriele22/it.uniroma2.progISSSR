package it.uniroma2.progisssr.rest;



import it.uniroma2.progisssr.controller.UserController;

import it.uniroma2.progisssr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserRestService {

    @Autowired
    private UserController userController;
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<User> createProduct(@RequestBody User user) {
       User newUser = userController.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
