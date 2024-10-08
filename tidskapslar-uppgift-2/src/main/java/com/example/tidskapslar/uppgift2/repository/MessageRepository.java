package com.example.tidskapslar.uppgift2.repository;


import com.example.tidskapslar.uppgift2.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}