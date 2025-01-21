package com.a207.smartlocker.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

// entity/Robot.java
@Entity
@Table(name = "robots")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Robot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long robotId;

    @Column(nullable = false)
    private String robotName;

    @Column(columnDefinition = "integer default 0")
    private Integer completedTasks;

    @Column(nullable = false)
    private LocalDateTime lastMaintenance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robot_status_id", nullable = false)
    private RobotStatus robotStatus;

}