package com.UBITDev.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UBITDev.model.Item;
import com.UBITDev.model.NGO;
import com.UBITDev.model.User;
import com.UBITDev.service.ItemService;
import com.UBITDev.service.NGOService;
import com.UBITDev.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    

    @Autowired
    private UserService userService;

    @Autowired

    private NGOService ngoService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId id) {
        String message = userService.deleteUser(id);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }



    // Items APIs for admin

    @GetMapping("/getitems")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = itemService.getItems();
        return new ResponseEntity<>(items,HttpStatus.OK);
    }





    // NGO APIs for admin


    @PostMapping("/createNGO")
    public ResponseEntity<?> createNGO(@RequestBody NGO ngo) {
        NGO createdNGO = ngoService.createNGO(ngo);
        return new ResponseEntity<>(createdNGO,HttpStatus.CREATED);
    }

    @GetMapping("/getNGOs")
    public ResponseEntity<List<NGO>> getNGOs() {
        List<NGO> ngos = ngoService.getNGOs();
        return new ResponseEntity<>(ngos,HttpStatus.OK);
    }

    @GetMapping("/getNGO/{id}")
    public ResponseEntity<NGO> getNGOById(@PathVariable ObjectId id) {
        NGO ngo = ngoService.getNGOById(id);
        return new ResponseEntity<>(ngo,HttpStatus.OK);
    }

    @DeleteMapping("/deleteNGO/{id}")
    public ResponseEntity<String> deleteNGO(@PathVariable ObjectId id) {
        String message = ngoService.deleteNGO(id);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PutMapping("/updateNGO/{id}")
    public ResponseEntity<NGO> updateNGO(@PathVariable ObjectId id,
                                        @RequestBody NGO ngo) {
        NGO updatedNGO = ngoService.updateNGO(id, ngo);
        return new ResponseEntity<>(updatedNGO,HttpStatus.ACCEPTED);
    }
    
}
