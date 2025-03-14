package com.reveture.Project1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_application")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_application_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "loan_type_id",nullable = false)
    private LoanType loanType;

    @Column(name = "principal_balance",nullable = false)
    private BigDecimal principalBalance;

    @Column(name = "term_length", nullable = false)
    private int termLength;

    @Column(name = "interest", nullable = false)
    private double interest;

    @Column(name = "total_balance", nullable = false)
    private BigDecimal totalBalance;

    @ManyToOne
    @JoinColumn(name = "application_status_id", nullable = false)
    private ApplicationStatus applicationStatus;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;
}

