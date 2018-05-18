package it.uniroma2.progisssr.rest;



import it.uniroma2.progisssr.controller.UserController;

import it.uniroma2.progisssr.dto.UserDto;
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

    @RequestMapping(path = "{username}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
        UserDto userUpdatedDto = null;
        try {
            userUpdatedDto = userController.updateUser(username, userDto);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(userUpdatedDto, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userUpdatedDto, HttpStatus.OK);
    }

    @RequestMapping(path = "{username} ", method = RequestMethod.GET)
    public ResponseEntity<UserDto> findUser(@PathVariable String username) throws EntitaNonTrovataException {
        UserDto userDto=null;
        try {
            userDto = userController.findUserById(username);
        }catch (EntitaNonTrovataException e){
            e.printStackTrace();
            return new ResponseEntity<>(userDto,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, userDto == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping (path = "{login}", method = RequestMethod.POST)
    public ResponseEntity<UserDto> login (@RequestBody UserDto userDto) throws EntitaNonTrovataException {
        UserDto userDtoauthenticated=null;
        try {
            userDtoauthenticated = userController.findUserById(userDto.getUsername());
        }catch (EntitaNonTrovataException e){
            e.printStackTrace();
            return new ResponseEntity<>(userDtoauthenticated,HttpStatus.NOT_FOUND);
        }
        if (userDtoauthenticated.getPassword().equals(userDto.getPassword())){
                return new ResponseEntity<>(userDtoauthenticated,HttpStatus.OK);
        }else
            return new ResponseEntity<>(userDtoauthenticated,HttpStatus.FOUND);
    }



    @RequestMapping(path = "{username}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable String id) {
        boolean deleted = userController.deleteUser(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> usersDto = userController.findAllUsers();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }
}
