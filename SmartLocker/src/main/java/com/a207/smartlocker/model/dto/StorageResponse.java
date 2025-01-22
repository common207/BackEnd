package com.a207.smartlocker.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StorageResponse {
    private Long lockerId;
    private Long tokenValue;
    private String message;
}
