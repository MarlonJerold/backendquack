package com.quackfinances.quackfinances.controller;

import com.quackfinances.quackfinances.model.UserModel;
import com.quackfinances.quackfinances.privates.JwtService;
import com.quackfinances.quackfinances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping
    public UserModel createAccount(@RequestBody UserModel user) {
        if (user != null) {

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            UserModel newUser = repository.save(user);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    newUser.getEmail(),
                    newUser.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            );

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            String jwtToken = jwtService.generateToken(authentication);

            System.out.printf(jwtToken);

            return newUser;
        } else {
            return null;
        }
    }
}