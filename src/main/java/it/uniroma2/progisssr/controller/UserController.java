package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.dto.TicketDto;
import it.uniroma2.progisssr.dto.UserDto;
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

    private User marshalling(UserDto userDto){
        User user = new User(userDto.getName(), userDto.getSurname(),
                userDto.getEmail(), userDto.getUsername(), userDto.getPassword(),
                userDto.getRole());
        /*for (String t: userDto.getTickets()) {
            user.addTickets(ticketDao.getOne(Long.parseLong(t)));
            System.out.println(ticketDao.getOne(Long.parseLong(t)));
        }*/
        /*if (userDao.existsById(userDto.getUsername())){
            User user = userDao.getOne(userDto.getUsername());
            user.update(new User(userDto.getName(), userDto.getSurname(),
                    userDto.getEmail(), userDto.getUsername(), userDto.getPassword(),
                    userDto.getRole()));
            return user;
        } else {
            User user = new User(userDto.getName(), userDto.getSurname(),
                    userDto.getEmail(), userDto.getUsername(), userDto.getPassword(),
                    userDto.getRole());
            return user;
        }*/
        return user;
    }

    private UserDto unmarshalling(User user){

        if(user!=null) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(user, UserDto.class);
        }else return  null;

    }

    @Transactional
    public @NotNull UserDto createUser(@NotNull UserDto userDto) {

        User newUser= userDao.save(marshalling(userDto));
        return unmarshalling(newUser);
    }

    @Transactional
    public UserDto updateUser(String username, UserDto userDto) throws EntitaNonTrovataException {
        User userToUpdate = userDao.getOne(username);
        if (userToUpdate == null)
            throw new EntitaNonTrovataException();
        /*System.out.println("userToUpdate\n\n" + userToUpdate.print());
        System.out.println("userDton\n\n" + marshalling(userDto).print());*/
        userToUpdate.update(marshalling(userDto));
        /*System.out.println("userToUpdate\n\n" + userToUpdate.print());*/
        User userUpdated = userDao.save(userToUpdate);
        return unmarshalling(userUpdated);
    }



    public UserDto findUserById(@NotNull String username) throws EntitaNonTrovataException {
        UserDto userDto;
        User user;
        try {
            user = userDao.getOne(username);
            userDto=unmarshalling(user);
        }catch (Exception e){
            e.printStackTrace();
            throw new EntitaNonTrovataException();
        }


        return userDto;
    }

    public boolean deleteUser(String username) {
        if (!userDao.existsById(username)) {
            return false;
        }
        userDao.deleteById(username);
        return true;
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userDao.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            usersDto.add(unmarshalling(users.get(i)));
        }
        return usersDto;
    }

    public List<Ticket> findTicketsById(String id) {
        User user = userDao.getOne(id);
        UserDto userDto = unmarshalling(user);
        List<Ticket> tickets = new ArrayList<>();
        for (String s: userDto.getTickets()) {
            tickets.add(ticketDao.getOne(Long.parseLong(s)));
        }
        return tickets;
    }
}
