package it.uniroma2.progisssr.rest;



import it.uniroma2.progisssr.controller.UserController;

import it.uniroma2.progisssr.dto.TicketDto;
import it.uniroma2.progisssr.dto.UserDto;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
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

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto newUserDto = userController.createUser(userDto);
        return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto userUpdatedDto = null;
        try {
            userUpdatedDto = userController.updateUser(id, userDto);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(userUpdatedDto, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userUpdatedDto, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> findUser(@PathVariable Long id) {
        UserDto userDto = userController.findUserById(id);
        return new ResponseEntity<>(userDto, userDto == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTicket(@PathVariable Long id) {
        boolean deleted = userController.deleteUser(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> usersDto = userController.findAllUsers();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }
}
