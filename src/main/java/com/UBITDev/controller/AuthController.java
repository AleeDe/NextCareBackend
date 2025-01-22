package com.UBITDev.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collection;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UBITDev.config.JwtProvider;
import com.UBITDev.model.USER_ROLE;
import com.UBITDev.model.User;
import com.UBITDev.repository.Userrepository;
import com.UBITDev.request.LoginRequest;
import com.UBITDev.response.AuthResponse;
import com.UBITDev.service.CustomerUserDetailsService;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private Userrepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
        User isEmailExist= userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new Exception("Email already exists");
        }else{
            User createduser=new User();
            createduser.setEmail(user.getEmail());
            createduser.setFullName(user.getFullName());
            createduser.setRole(user.getRole());
            createduser.setPassword(passwordEncoder.encode(user.getPassword()));

            User savedUser= userRepository.save(createduser);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt= jwtProvider.generateToken(authentication);
            
            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwt);
            authResponse.setMessage("Registration Successfully");
            authResponse.setRole(savedUser.getRole());

            return new ResponseEntity<>(authResponse,HttpStatus.CREATED);


                    }
        }
    
    

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){

        String userName= req.getEmail();
        String password= req.getPassword();
        
        Authentication authentication = authentication(userName, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role= authorities.isEmpty()?null : authorities.iterator().next().getAuthority();

        String jwt= jwtProvider.generateToken(authentication);
            
            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwt);
            authResponse.setMessage("Login Successfully");
            authResponse.setRole(USER_ROLE.valueOf(role));

            return new ResponseEntity<>(authResponse,HttpStatus.OK);
            }
        
            private Authentication authentication(String userName, String password) {
                 
                UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
                if(userDetails == null){
                    throw new BadCredentialsException("invalid username");
                }else{
                    if(!passwordEncoder.matches(password, userDetails.getPassword())){
                        throw new BadCredentialsException("invalid password");
                    }else{
                        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    }

                }

            }
        }