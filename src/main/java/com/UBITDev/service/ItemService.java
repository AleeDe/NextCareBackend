package com.UBITDev.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UBITDev.config.JwtProvider;
import com.UBITDev.model.Item;
import com.UBITDev.model.User;
import com.UBITDev.repository.ItemRepository;
import com.UBITDev.repository.Userrepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;


    @Autowired
    private JwtProvider jwtProvider;

    @Autowired 
    private UserService userService;

    @Autowired 
    private Userrepository userRepository;

    public Item createItem(Item item, String jwt) {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User currentUser = userRepository.findByEmail(email);

        Item savedItem = itemRepository.save(item);

        List<Item> items = currentUser.getItems();
        items.add(savedItem);

        currentUser.setItems(items);
        userRepository.save(currentUser);
        
        return savedItem;
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(ObjectId id,String jwt) {
        jwtProvider.getEmailFromJwtToken(jwt);
        return itemRepository.findById(id).orElse(null);
    }

    public String deleteItem(ObjectId id,String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User currentUser = userRepository.findByEmail(email);

        Item item = itemRepository.findById(id).orElse(null);

        List<Item> items = currentUser.getItems();
        items.remove(item);

        currentUser.setItems(items);
        userRepository.save(currentUser);
        itemRepository.deleteById(id);
        return "Item deleted successfully";
    }

    public Item updateItem(Item item, ObjectId id,String jwt) {

        jwtProvider.getEmailFromJwtToken(jwt);
        Item currentItem = itemRepository.findById(id).orElse(null);
        currentItem.setTitle(item.getTitle());
        currentItem.setDescription(item.getDescription());
        currentItem.setCategory(item.getCategory());
        currentItem.setCondition(item.getCondition());

        return itemRepository.save(currentItem);
    }

    public Item updateItemStatus(ObjectId id, String status, String jwt) {
        jwtProvider.getEmailFromJwtToken(jwt);
        Item currentItem = itemRepository.findById(id).orElse(null);
        currentItem.setStatus(status);
        return itemRepository.save(currentItem);
    }
    
}
