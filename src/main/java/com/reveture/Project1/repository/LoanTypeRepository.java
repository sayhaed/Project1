package com.reveture.Project1.repository;

import com.reveture.Project1.entity.LoanApplication;
import com.reveture.Project1.entity.LoanType;
import com.reveture.Project1.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
//    findById(){}
    Optional<LoanType> findById(Long Id);
}