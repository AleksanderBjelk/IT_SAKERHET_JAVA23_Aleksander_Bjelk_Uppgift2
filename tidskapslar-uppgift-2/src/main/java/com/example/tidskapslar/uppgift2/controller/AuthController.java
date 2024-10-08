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
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    private static final String SECRET = "RmV2dDJDZzJ5MkVma1B4R3lNdE1qYzBHRnBzYklBUTA=";

    @Autowired
    private UserService userService;

    // Autentisera användare och generera JWT
    @PostMapping("/authenticate")
    public Map<String, String> authenticate(@RequestBody Map<String, Object> credentials) throws JOSEException {
        String email = (String) credentials.get("email");
        String password = (String) credentials.get("password");

        logger.info("Received login request for email: " + email);  // Logga när förfrågan tas emot

        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && userService.checkPassword(user.get(), password)) {
            logger.info("Login successful for email: " + email);  // Logga om inloggning lyckas

            // Generera JWT-token om inloggningen lyckas
            JWSSigner signer = new MACSigner(SECRET);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issuer("https://yourapp.com")
                    .expirationTime(new Date(new Date().getTime() + 60 * 1000 * 60))  // Token giltig i 1 timme
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            String jwt = signedJWT.serialize();

            logger.info("JWT generated for email: " + email);  // Logga att JWT skapades
            return Map.of("token", jwt);
        } else {
            logger.warning("Invalid credentials for email: " + email);  // Logga om inloggningen misslyckas
            return Map.of("error", "Invalid credentials");
        }
    }
}