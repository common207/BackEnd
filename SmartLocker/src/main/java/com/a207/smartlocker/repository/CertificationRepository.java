package com.a207.smartlocker.repository;

import com.a207.smartlocker.model.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    Optional<Certification> findByAdminIdAndAdminPassword(String adminId, String adminPassword);
}
