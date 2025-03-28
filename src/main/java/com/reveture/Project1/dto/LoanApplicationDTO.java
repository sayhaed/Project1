package com.reveture.Project1.dto;

import com.reveture.Project1.entity.ApplicationStatus;
import com.reveture.Project1.entity.LoanType;
import com.reveture.Project1.entity.UserProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {


//    @NotBlank(message = "Password is required")
//    @Size(min = 8, message = "Password must be at least 8 characters")
//    private String password;

    @NotNull(message = "Loan type is mandatory")
    private LoanType loanType;

    @NotNull(message = "Balance is required ")
    private BigDecimal principalBalance;

    @NotNull(message = "Term lenght is required")
    private int termLength;


}
