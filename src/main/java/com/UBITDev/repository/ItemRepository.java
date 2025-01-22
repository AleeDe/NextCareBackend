package com.UBITDev.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.UBITDev.model.Item;

public interface ItemRepository extends MongoRepository<Item,ObjectId> {
    
}
