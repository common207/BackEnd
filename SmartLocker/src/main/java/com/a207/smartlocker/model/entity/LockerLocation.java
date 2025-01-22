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
    private int locationId; // Matches DB SERIAL as INTEGER

    @Column(nullable = false)
    private String locationName; // Non-nullable

    @Column(nullable = false)
    private int totalLockers; // Non-nullable INTEGER

    @Column(nullable = false)
    private int availableLockers; // Non-nullable INTEGER
}