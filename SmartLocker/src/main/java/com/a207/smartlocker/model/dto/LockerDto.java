package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/LockerDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerDto {
    private int lockerId;
    private int lockerStatusId;
    private int lockerLocationId;
    private int tokenId;
}
