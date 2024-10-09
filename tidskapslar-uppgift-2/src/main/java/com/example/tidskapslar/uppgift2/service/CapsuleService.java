package com.example.tidskapslar.uppgift2.service;

import com.example.tidskapslar.uppgift2.entity.Capsule;
import com.example.tidskapslar.uppgift2.entity.User;
import com.example.tidskapslar.uppgift2.repository.CapsuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapsuleService {

    @Autowired
    private CapsuleRepository capsuleRepository;

    public Capsule createCapsule(String name, User user) {
        Capsule capsule = new Capsule();
        capsule.setName(name);
        capsule.setUser(user);
        return capsuleRepository.save(capsule);
    }

    public List<Capsule> getUserCapsules(Long userId) {
        return capsuleRepository.findByUserId(userId);
    }

    public Optional<Capsule> getCapsuleById(Long capsuleId) {
        return capsuleRepository.findById(capsuleId);
    }
}