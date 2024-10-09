package com.example.tidskapslar.uppgift2.controller;

import com.example.tidskapslar.uppgift2.entity.Message;
import com.example.tidskapslar.uppgift2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    //skriver ett meddelande och krypterar det med AES
    @PostMapping("/create")
    public ResponseEntity<String> createMessage(@RequestBody Map<String, String> payload) {
        String messageContent = payload.get("message");
        Long userId = Long.valueOf(payload.get("userId"));

        boolean success = messageService.createMessage(userId, messageContent);
        if (success) {
            return ResponseEntity.ok("Meddelandet har krypterats och sparats.");
        } else {
            return ResponseEntity.badRequest().body("Misslyckades med att spara meddelandet.");
        }
    }

    //endpoint för att hämta alla användarens meddelande
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<String>> getUserMessages(@PathVariable Long userId) {
        List<Message> messages = messageService.getMessages(userId);
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        //dekrypteringg
        List<String> decryptedMessages = messages.stream()
                .map(msg -> messageService.decryptMessage(msg.getEncryptedMessage(), msg.getSecretKey()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(decryptedMessages);
    }
}