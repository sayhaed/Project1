package com.reveture.Project1.repository;

import com.reveture.Project1.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);  // Para buscar usuarios por email
    boolean existsByEmail(String email);  // Para validar si el email ya está registrado
}