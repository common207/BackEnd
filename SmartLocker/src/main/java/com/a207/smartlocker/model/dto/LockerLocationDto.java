package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/LockerLocationDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerLocationDto {
    private int locationId;
    private String locationName;
    private int totalLockers;
    private int availableLockers;
}
