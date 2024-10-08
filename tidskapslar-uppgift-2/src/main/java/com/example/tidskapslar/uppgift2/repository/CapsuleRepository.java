package com.example.tidskapslar.uppgift2.repository;


import com.example.tidskapslar.uppgift2.entity.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CapsuleRepository extends JpaRepository<Capsule, Long> {
    List<Capsule> findByUserId(Long userId);
}