package com.a207.smartlocker.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lockers") // 데이터베이스 테이블 이름과 일치시킴
@Getter
@Setter
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lockerId;

    @ManyToOne
    @JoinColumn(name = "token_id")
    private AccessToken tokenId;

    public Long getTokenId() {
        return tokenId != null ? tokenId.getTokenId() : null;
    }
}
