package com.reveture.Project1.repository;

import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.LoanApplication;
import com.reveture.Project1.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long>{
    Optional<List<LoanApplication>> findByUserProfile(UserProfile userProfile);
    Optional<LoanApplication> findByIdAndUserProfile(Long Id, UserProfile userProfile);
}
