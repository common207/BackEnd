package com.a207.smartlocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.a207.smartlocker.model.entity.Locker;

public interface LockerRepository extends JpaRepository<Locker, Long> {

}