package com.example.tidskapslar.uppgift2.service;

import com.example.tidskapslar.uppgift2.entity.Message;
import com.example.tidskapslar.uppgift2.entity.User;
import com.example.tidskapslar.uppgift2.repository.MessageRepository;
import com.example.tidskapslar.uppgift2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String AES = "AES";

    //krypterar meddelande och spara i databasen
    public boolean createMessage(Long userId, String messageContent) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Kryptera meddelandet med AES
            String[] encryptedData = encryptMessage(messageContent);
            String encryptedMessage = encryptedData[0];
            String secretKey = encryptedData[1];

            //skapar och spara meddelandet i databasen
            Message message = new Message();
            message.setEncryptedMessage(encryptedMessage);
            message.setUser(user);
            message.setSecretKey(secretKey);  //nyckeln förljer

            messageRepository.save(message);
            return true;
        }
        return false;
    }

    //hämtar meddelanden för en användare
    public List<Message> getMessages(Long userId) {
        return messageRepository.findByUserId(userId);
    }

    //dekryptering
    public String decryptMessage(String encryptedMessage, String secretKeyString) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
            byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Dekrypteringen misslyckades", e);
        }
    }

    //kryptera meddelandet och returnera både det krypterade meddelandet och nyckeln
    private String[] encryptMessage(String messageContent) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES);
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(messageContent.getBytes());


            String encryptedMessage = Base64.getEncoder().encodeToString(encryptedBytes);
            String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            return new String[]{encryptedMessage, secretKeyBase64}; //returnerar både det krypterade meddelandet och nyckeln
        } catch (Exception e) {
            throw new RuntimeException("Krypteringen misslyckades", e);
        }
    }
}