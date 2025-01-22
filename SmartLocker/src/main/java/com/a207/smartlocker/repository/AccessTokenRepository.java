package com.a207.smartlocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.a207.smartlocker.model.entity.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

}
