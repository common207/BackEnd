package com.a207.smartlocker.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

// entity/AccessToken.java
@Entity
@Table(name = "access_tokens")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId;

    @Column(nullable = false)
    private int tokenValue;
}