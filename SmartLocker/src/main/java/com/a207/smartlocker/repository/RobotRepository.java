package com.a207.smartlocker.repository;

import com.a207.smartlocker.model.entity.Robot;
import com.a207.smartlocker.model.entity.RobotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RobotRepository extends JpaRepository<Robot, Long> {
    Optional<Robot> findByRobotStatus(RobotStatus status);
}

