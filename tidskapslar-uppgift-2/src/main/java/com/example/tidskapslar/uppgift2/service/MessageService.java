package com.example.tidskapslar.uppgift2.service;

import com.example.tidskapslar.uppgift2.dto.MessageRequestDto;
import com.example.tidskapslar.uppgift2.entity.Capsule;
import com.example.tidskapslar.uppgift2.entity.Message;
import com.example.tidskapslar.uppgift2.repository.CapsuleRepository;
import com.example.tidskapslar.uppgift2.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CapsuleRepository capsuleRepository;

    public void createMessage(MessageRequestDto messageRequestDto) {
        Optional<Capsule> capsuleOpt = capsuleRepository.findById(messageRequestDto.getCapsuleId());
        if (!capsuleOpt.isPresent()) {
            throw new RuntimeException("Capsule not found");
        }

        Capsule capsule = capsuleOpt.get();
        Message message = new Message();
        message.setContent(messageRequestDto.getContent());
        message.setCapsule(capsule);

        messageRepository.save(message);
    }

    public List<Message> getMessagesByCapsuleId(Long capsuleId) {
        return messageRepository.findByCapsuleId(capsuleId);
    }
}