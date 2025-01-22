package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StorageRequest {
    private Long lockerID;
    private String phoneNumber;
}
