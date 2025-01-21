package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/AccessTokenDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {
    private int tokenId;
    private int tokenValue;
}
