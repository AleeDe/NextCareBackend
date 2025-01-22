package com.UBITDev.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "NGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NGO {

    @Id
    private ObjectId id;

    private String name;
    private String email;
    private String logoUrl;
    private String description;
    
}
