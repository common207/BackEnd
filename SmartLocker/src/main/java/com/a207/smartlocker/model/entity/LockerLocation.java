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
    private Long locationId; // Matches DB SERIAL as LongEGER

    @Column(nullable = false)
    private String locationName; // Non-nullable

    @Column(nullable = false)
    private Long totalLockers; // Non-nullable LongEGER

    @Column(nullable = false)
    private Long availableLockers; // Non-nullable LongEGER
}