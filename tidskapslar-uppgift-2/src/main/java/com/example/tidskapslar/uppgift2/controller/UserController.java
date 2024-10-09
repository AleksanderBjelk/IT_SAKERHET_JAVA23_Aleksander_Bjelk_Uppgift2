//package com.example.tidskapslar.uppgift2.controller;
//
//
//import com.example.tidskapslar.uppgift2.dto.UserRegistrationDto;
//import com.example.tidskapslar.uppgift2.entity.User;
//import com.example.tidskapslar.uppgift2.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    //registrera en ny användare
//    @PostMapping("/register")
//    public String registerUser(@RequestBody UserRegistrationDto registrationDto) {
//        Optional<User> existingUser = userService.findByEmail(registrationDto.getEmail());
//        if (existingUser.isPresent()) {
//            return "Email already in use.";
//        }
//        userService.registerUser(registrationDto.getEmail(), registrationDto.getPassword());
//        return "User registered successfully.";
//    }
//}