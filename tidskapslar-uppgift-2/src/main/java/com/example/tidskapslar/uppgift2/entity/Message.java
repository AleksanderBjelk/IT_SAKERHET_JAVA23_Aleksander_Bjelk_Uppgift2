package com.example.tidskapslar.uppgift2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_id")
    private Capsule capsule;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String encryptedMessage;

    public void setContent(String content) {
        this.encryptedMessage = content;
    }
    public String getContent() {
        return encryptedMessage;
    }

    public void setCapsule(Capsule capsule) {
        this.capsule = capsule;
    }
    public Capsule getCapsule() {
        return capsule;
    }


    // Getters and Setters
}