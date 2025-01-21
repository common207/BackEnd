package com.a207.smartlocker.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "locker_usage_logs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerUsageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id", nullable = false)
    private Locker locker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime storeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_robot_id")
    private Robot storeRobot;

    private LocalDateTime retrieveTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retrieve_robot_id")
    private Robot retrieveRobot;
}