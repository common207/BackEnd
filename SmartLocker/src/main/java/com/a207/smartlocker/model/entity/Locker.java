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
    private Long lockerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_status_id", nullable = false)
    private LockerStatus lockerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_location_id", nullable = false)
    private LockerLocation lockerLocation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private AccessToken token;
}