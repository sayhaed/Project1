package com.reveture.Project1.repository;

import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByAccountId(Long accountId);
    boolean existsByAccount(Account account);
}
