package com.example.tidskapslar.uppgift2.repository;

import com.example.tidskapslar.uppgift2.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByCapsuleId(Long capsuleId);
}