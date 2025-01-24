package com.a207.smartlocker.model.entity;

import lombok.*;
import jakarta.persistence.*;

// entity/AccessToken.java
@Entity
@Table(name = "access_tokens")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(nullable = false)
    private Long tokenValue;
}