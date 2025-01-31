package com.a207.smartlocker.repository;

import com.a207.smartlocker.model.entity.Robot;
import com.a207.smartlocker.model.entity.RobotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RobotRepository extends JpaRepository<Robot, Long> {
    @Transactional
    @Query(value = "UPDATE robots r SET robot_status_id = :newStatusId " +
            "WHERE r.robot_id IN (SELECT r2.robot_id FROM robots r2 WHERE r2.robot_status_id = :currentStatusId LIMIT 1) " +
            "RETURNING *",
            nativeQuery = true)
    Optional<Robot> findAndUpdateRobotStatus(@Param("currentStatusId") Long currentStatusId,
                                             @Param("newStatusId") Long newStatusId);
}

