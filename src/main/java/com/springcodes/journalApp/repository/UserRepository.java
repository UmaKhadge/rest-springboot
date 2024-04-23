package com.springcodes.journalApp.repository;

import com.springcodes.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>
{
    //Creating method to find username
    User findByUserName(String username);
}
