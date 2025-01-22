package com.UBITDev.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UBITDev.model.Item;
import com.UBITDev.model.User;
import com.UBITDev.service.ItemService;
import com.UBITDev.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                            @RequestHeader("Authorization") String jwt) {
        User updatedUser = userService.updateUser(user, jwt);
        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }



    // Item APIs for user


    @PostMapping("/createItem")
    public ResponseEntity<?> createItem(@RequestBody Item item,
                                            @RequestHeader("Authorization") String jwt) {
        Item createdItem = itemService.createItem(item, jwt);
        return new ResponseEntity<>(createdItem,HttpStatus.CREATED);
    }

    @GetMapping("/getItems/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable ObjectId id,@RequestHeader("Authorization") String jwt) {
        Item item = itemService.getItemById(id,jwt);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable ObjectId id,@RequestHeader("Authorization") String jwt) {
        String message = itemService.deleteItem(id,jwt);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable ObjectId id,
                                            @RequestBody Item item,
                                            @RequestHeader("Authorization") String jwt) {
        Item updatedItem = itemService.updateItem(item, id, jwt);
        return new ResponseEntity<>(updatedItem,HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateItemStatus/{id}")
    public ResponseEntity<Item> updateItemStatus(@PathVariable ObjectId id,
                                            @RequestParam String status,
                                            @RequestHeader("Authorization") String jwt) {
        Item updatedItem = itemService.updateItemStatus(id, status, jwt);
        return new ResponseEntity<>(updatedItem,HttpStatus.ACCEPTED);
 
    }
}