package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/UserDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;          // Primary Key (Non-nullable)
    private String phoneNumber;  // Unique, Non-nullable
}