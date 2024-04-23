package com.springcodes.journalApp.service;

import com.springcodes.journalApp.entity.User;
import com.springcodes.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    //in this class we write all the methods to be used in controller class
    @Autowired
    private UserRepository userRepository;

    //method to save entry which will be called in controller (this method we are creating not inbuilt)
    public void saveEntry(User user) {
        //this save method is in built method
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id)
    {
        userRepository.deleteById(id);
    }

    //calling method in user repository
    //this method is called in JournalEntryController
    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }


}
