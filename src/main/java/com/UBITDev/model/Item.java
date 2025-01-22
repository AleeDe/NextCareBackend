package com.UBITDev.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    private ObjectId id;

    private String title;
    private String description;
    private String category;
    private String condition;
    private String ngoName;

    private String status="available";
    
}
