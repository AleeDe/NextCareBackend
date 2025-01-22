package com.UBITDev.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.UBITDev.model.NGO;

public interface NGORepository extends MongoRepository<NGO, ObjectId> {
    
}
