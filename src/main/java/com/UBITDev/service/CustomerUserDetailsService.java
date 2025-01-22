package com.UBITDev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.UBITDev.model.USER_ROLE;
import com.UBITDev.model.User;
import com.UBITDev.repository.Userrepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private Userrepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username);
        if(user!=null){
            USER_ROLE role=user.getRole();
            List<GrantedAuthority> authorities=new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.toString()));
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
        }
        else {
            throw new UsernameNotFoundException("user not found with email "+username);
        }

    }
}
