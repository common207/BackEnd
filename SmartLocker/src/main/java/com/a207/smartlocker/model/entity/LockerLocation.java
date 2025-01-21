package com.a207.smartlocker.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

// entity/LockerLocation.java
@Entity
@Table(name = "locker_locations")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private Integer totalLockers;

    @Column(nullable = false)
    private Integer availableLockers;
}