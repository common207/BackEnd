package com.a207.smartlocker.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

// entity/LockerStatus.java
@Entity
@Table(name = "locker_status")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lockerStatusId;

    @Column(nullable = false)
    private String lockerStatus;
}