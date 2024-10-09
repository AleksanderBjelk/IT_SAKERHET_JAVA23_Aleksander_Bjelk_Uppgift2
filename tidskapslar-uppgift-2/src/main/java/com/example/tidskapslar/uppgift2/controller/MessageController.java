package com.example.tidskapslar.uppgift2.controller;

import com.example.tidskapslar.uppgift2.dto.MessageRequestDto;
import com.example.tidskapslar.uppgift2.entity.Message;
import com.example.tidskapslar.uppgift2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<String> createMessage(@RequestBody MessageRequestDto messageRequestDto) {
        messageService.createMessage(messageRequestDto);
        return ResponseEntity.ok("Message created successfully");
    }

    @GetMapping("/capsule/{capsuleId}")
    public ResponseEntity<List<Message>> getMessagesByCapsuleId(@PathVariable Long capsuleId) {
        List<Message> messages = messageService.getMessagesByCapsuleId(capsuleId);
        return ResponseEntity.ok(messages);
    }
}