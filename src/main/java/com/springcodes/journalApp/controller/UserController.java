package com.springcodes.journalApp.controller;

import com.springcodes.journalApp.entity.User;
import com.springcodes.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    //injecting UserService class
    @Autowired
    private UserService userService;

    //writing method to get all the user list
    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getAll();
    }

    //method to create new user
    @PostMapping
    public void createUser(@RequestBody User user)
    {
        userService.saveEntry(user);
    }

    //method to update user info new password or new username
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User userInDb = userService.findByUserName(userName);
        if (userInDb != null)
        {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
