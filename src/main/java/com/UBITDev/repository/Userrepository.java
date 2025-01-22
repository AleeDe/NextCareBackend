package com.UBITDev.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.UBITDev.model.User;

public interface Userrepository extends MongoRepository<User,ObjectId> {
    public User findByEmail(String username);
    
}
