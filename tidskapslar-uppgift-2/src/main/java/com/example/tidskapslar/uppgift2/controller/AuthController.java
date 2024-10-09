package com.example.tidskapslar.uppgift2.controller;

import com.example.tidskapslar.uppgift2.entity.User;
import com.example.tidskapslar.uppgift2.service.UserService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    private static final String SECRET = "RmV2dDJDZzJ5MkVma1B4R3lNdE1qYzBHRnBzYklBUTA=";

    @Autowired
    private UserService userService;

    //registreara användare
    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            return "Email already in use.";
        }

        userService.registerUser(email, password);
        return "User registered successfully.";
    }

    //inloggning och fixat JWT
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) throws JOSEException {
        String email = credentials.get("email");
        String password = credentials.get("password");

        logger.info("Received login request for email: " + email);

        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && userService.checkPassword(user.get(), password)) {
            logger.info("Login successful for email: " + email);

            JWSSigner signer = new MACSigner(SECRET);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issuer("https://yourapp.com")
                    .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            String jwt = signedJWT.serialize();

            logger.info("JWT generated for email: " + email);
            return Map.of("token", jwt, "userId", String.valueOf(user.get().getId()));  //returnerar token och userId
        } else {
            logger.warning("Invalid credentials for email: " + email);
            return Map.of("error", "Invalid credentials");
        }
    }
}