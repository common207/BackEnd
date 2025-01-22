package com.a207.smartlocker.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

// entity/Locker.java
@Entity
@Table(name = "lockers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lockerId; // Matches DB SERIAL as INTEGER

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_status_id", nullable = false)
    private LockerStatus lockerStatus; // Non-nullable Foreign Key reference

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_location_id", nullable = false)
    private LockerLocation lockerLocation; // Non-nullable Foreign Key reference

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id", nullable = true) // Nullable Foreign Key reference
    private AccessToken token;
}