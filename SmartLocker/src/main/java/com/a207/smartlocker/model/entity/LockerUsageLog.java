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
    private Long logId; // Matches DB SERIAL as LongEGER

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id", nullable = false)
    private Locker locker; // Non-nullable Foreign Key reference

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Non-nullable Foreign Key reference

    @Column(nullable = false)
    private LocalDateTime storeTime; // Non-nullable

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_robot_id", nullable = false)
    private Robot storeRobot; // Non-nullable Foreign Key reference

    @Column
    private LocalDateTime retrieveTime; // Nullable

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retrieve_robot_id")
    private Robot retrieveRobot; // Nullable Foreign Key reference
}