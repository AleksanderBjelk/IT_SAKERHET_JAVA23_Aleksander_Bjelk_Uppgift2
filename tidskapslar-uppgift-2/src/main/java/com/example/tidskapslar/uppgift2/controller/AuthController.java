package com.example.tidskapslar.uppgift2.controller;


import com.example.tidskapslar.uppgift2.entity.User;
import com.example.tidskapslar.uppgift2.service.UserService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    private static final String SECRET = "RmV2dDJDZzJ5MkVma1B4R3lNdE1qYzBHRnBzYklBUTA=";

    @Autowired
    private UserService userService;

    // Autentisera användare och generera JWT
    @PostMapping("/authenticate")
    public Map<String, String> authenticate(@RequestBody Map<String, Object> credentials) throws JOSEException {
        String email = (String) credentials.get("email");
        String password = (String) credentials.get("password");

        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && userService.checkPassword(user.get(), password)) {
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

            return Map.of("token", jwt);
        } else {
            return Map.of("error", "Invalid credentials");
        }
    }

    // Skyddad endpoint som kräver JWT
    @GetMapping("/protected")
    public Map<String, Object> getProtectedData(@RequestHeader(name = "Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);  // Ta bort "Bearer " prefixet

            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                JWSVerifier verifier = new MACVerifier(SECRET);

                if (signedJWT.verify(verifier) && new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime())) {
                    return Map.of("data", "This is protected data.");
                } else {
                    return Map.of("error", "Invalid or expired JWT");
                }
            } catch (ParseException | JOSEException e) {
                return Map.of("error", "Error parsing or verifying JWT");
            }
        }
        return Map.of("error", "No authorization header found");
    }
}