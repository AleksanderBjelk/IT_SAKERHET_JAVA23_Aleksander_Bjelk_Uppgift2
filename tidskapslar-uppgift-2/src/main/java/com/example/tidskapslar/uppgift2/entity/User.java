package com.example.tidskapslar.uppgift2.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")  // Tillbaka till "user"
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Capsule> capsules;

    // Getters och Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}