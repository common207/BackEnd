package com.a207.smartlocker.model.dto;

import lombok.*;

// dto/LockerDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerDto {
    private Long lockerId;           // Primary Key (Non-nullable)
    private Long lockerStatusId;     // Foreign Key (Non-nullable)
    private Long lockerLocationId;   // Foreign Key (Non-nullable)
    private Long tokenId;        // Foreign Key (Nullable)
}
