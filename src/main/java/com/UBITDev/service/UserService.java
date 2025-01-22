package com.UBITDev.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UBITDev.config.JwtProvider;
import com.UBITDev.model.User;
import com.UBITDev.repository.Userrepository;

@Service
public class UserService {

    @Autowired
    Userrepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return findUserByEmail(email);
    }


    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
        throw new Exception("user does not exist");
        }else{
            return user;
        }
    }

    public User updateUser(User user,String jwt) {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User currentUser = userRepository.findByEmail(email);

        currentUser.setFullName(user.getFullName());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setProfileImageURL(user.getProfileImageURL());
        
        return userRepository.save(currentUser);
    }

    public String deleteUser(ObjectId id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    
}