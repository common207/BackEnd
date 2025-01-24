package com.a207.smartlocker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;


@Entity
@Table(name = "lockers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})

public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lockerId;

    @ManyToOne
    @JoinColumn(name = "token_id")
    private AccessToken tokenId;


    public Long getTokenId() {
        return tokenId != null ? tokenId.getTokenId() : null;
    }
}

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id", nullable = true) // Nullable Foreign Key reference
    private AccessToken token;

    public void updateStatus(LockerStatus lockerStatus) {
        this.lockerStatus = lockerStatus;
    }

    public void updateToken(AccessToken token) {
        this.token = token;
    }
}

