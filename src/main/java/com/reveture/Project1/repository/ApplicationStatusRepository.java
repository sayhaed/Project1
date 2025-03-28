package com.reveture.Project1.repository;

import com.reveture.Project1.entity.ApplicationStatus;
import com.reveture.Project1.entity.LoanApplication;
import com.reveture.Project1.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
    Optional<ApplicationStatus> findById(Long Id);
}
