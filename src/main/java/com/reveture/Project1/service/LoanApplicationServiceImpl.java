package com.reveture.Project1.service;

import com.reveture.Project1.dto.LoanApplicationDTO;
import com.reveture.Project1.entity.*;
import com.reveture.Project1.repository.ApplicationStatusRepository;
import com.reveture.Project1.repository.LoanApplicationRepository;
import com.reveture.Project1.repository.LoanTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private LoanTypeRepository loanTypeRepository;
    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;
    @Autowired
    private AccountService accountService;

//    @Autowired
//    private LoanApplicationRepository loanApplicationRepository;
/// ////////////////////////// Start Implementation
/// Create LOAN
    @Override
    public LoanApplication createLoan(LoanApplicationDTO loanApplicationDTO, String email) {
        Account account = accountService.getAccountByEmail(email);
        UserProfile userProfile = userProfileService.getUserByAccountId(account.getId());
         Long id = 1L;
        ApplicationStatus applicationStatus = applicationStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Status not found"));

        LoanType loanType = loanTypeRepository.findById(loanApplicationDTO.getLoanType().getId())
                .orElseThrow(() -> new RuntimeException("Loan Type not found"));

        LoanApplication loanToCreate = new LoanApplication();
        loanToCreate.setLoanType(loanType);
        loanToCreate.setPrincipalBalance(loanApplicationDTO.getPrincipalBalance());
        loanToCreate.setTermLength(loanApplicationDTO.getTermLength());
        loanToCreate.setInterest(10);
        loanToCreate.setTotalBalance(loanApplicationDTO.getPrincipalBalance().multiply(BigDecimal.valueOf(1.1)));
        loanToCreate.setApplicationStatus(applicationStatus);
        loanToCreate.setUserProfile(userProfile);
        loanToCreate.setApplicationDate(LocalDate.now());
    return  loanApplicationRepository.save(loanToCreate);
    }
/// //////Get LOANS
    @Override
    public List<LoanApplication> getAllLoans() {
        return loanApplicationRepository.findAll();
    }
    @Override
    public List<LoanApplication> getLoansByMail(String email){
        Account account = accountService.getAccountByEmail(email);
        UserProfile userProfile = userProfileService.getUserByAccountId(account.getId());
        return loanApplicationRepository.findByUserProfile(userProfile)
                .orElseThrow(() -> new RuntimeException("There is no any loan for mail: "+email));
    }
    @Override public LoanApplication  getLoanById(Long id){
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no loan to show or you are not allowed to see the id: " + id));
    }
    @Override public LoanApplication  getLoanByIdAndUser(Long id, String mail){

        Account account = accountService.getAccountByEmail(mail);
        UserProfile userProfile = userProfileService.getUserByAccountId(account.getId());

//        UserProfile userProfile = new UserProfile();
        return loanApplicationRepository.findByIdAndUserProfile(id, userProfile)
                .orElseThrow(() -> new RuntimeException("There is no loan to show or you are not allowed to see the id: " + id));
    }
    /// ////////// UPDATE LOAN
    @Override public LoanApplication updateLoanById(Long id){
        return null;
    }
    @Override public LoanApplication approveLoanById(Long id){
        return null;
    }
}
