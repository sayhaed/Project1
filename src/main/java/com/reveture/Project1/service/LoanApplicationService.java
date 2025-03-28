package com.reveture.Project1.service;

import com.reveture.Project1.dto.LoanApplicationDTO;
import com.reveture.Project1.entity.LoanApplication;

import java.util.List;
import java.util.Optional;


public interface LoanApplicationService{
//
        LoanApplication createLoan(LoanApplicationDTO loanApplicationDTO, String email);
        List<LoanApplication> getAllLoans();
        List<LoanApplication> getLoansByMail(String mail);
        LoanApplication getLoanById(Long id);
        LoanApplication getLoanByIdAndUser(Long id, String mail);
        LoanApplication approveLoan(Long id);
        LoanApplication rejectLoan(Long id);

    }
