package com.example.tidskapslar.uppgift2.controller;

import com.example.tidskapslar.uppgift2.entity.Capsule;
import com.example.tidskapslar.uppgift2.entity.User;
import com.example.tidskapslar.uppgift2.service.CapsuleService;
import com.example.tidskapslar.uppgift2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/capsules")
public class CapsuleController {

    @Autowired
    private UserService userService;

    @Autowired
    private CapsuleService capsuleService;

    @PostMapping("/create")
    public Capsule createCapsule(@RequestBody Map<String, Object> payload) {
        String capsuleName = (String) payload.get("name");
        Long userId = Long.valueOf(payload.get("userId").toString());

        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return capsuleService.createCapsule(capsuleName, user);
    }

    @GetMapping("/user/{userId}")
    public List<Capsule> getUserCapsules(@PathVariable Long userId) {
        return capsuleService.getUserCapsules(userId);
    }

    @GetMapping("/{capsuleId}")
    public Optional<Capsule> getCapsuleById(@PathVariable Long capsuleId) {
        return capsuleService.getCapsuleById(capsuleId);
    }
}