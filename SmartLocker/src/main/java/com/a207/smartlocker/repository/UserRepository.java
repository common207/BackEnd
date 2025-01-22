package com.a207.smartlocker.repository;

import com.a207.smartlocker.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}