package com.reveture.Project1.controller;

import com.reveture.Project1.dto.LoginRequestDTO;
//import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.*;
import com.reveture.Project1.dto.LoanApplicationDTO;
//import com.reveture.Project1.service.AccountService;
import com.reveture.Project1.repository.LoanApplicationRepository;
import com.reveture.Project1.repository.LoanTypeRepository;
import com.reveture.Project1.service.AccountService;
import com.reveture.Project1.service.LoanApplicationService;
import com.reveture.Project1.service.UserProfileService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/loans")
public class LoanApplicationController {
    @Autowired
    private LoanApplicationService loanApplicationService;
    @Autowired
    private AccountService accountService;

/// /////////////////////////////////
private String getMail(){
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    return email;
}

    private Boolean isAdmin(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Account requester = accountService.getAccountByEmail(email);
        return "Admin".equalsIgnoreCase(requester.getAccountType().getType());
    }
    private Boolean isLoged(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }
        return true;}
/// //////Crete loans
    @GetMapping()
    public ResponseEntity<LoanApplication>
//    public LoanApplication
    createLoan(HttpSession session, @Valid @RequestBody LoanApplicationDTO loanApplicationDTO) {

        boolean isAdmin = isAdmin();
        boolean isLoged = isLoged(session);

        if(!isLoged) throw new RuntimeException("You're not logged in");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        LoanApplication loanToCreate = loanApplicationService.createLoan(loanApplicationDTO, email);

        System.out.println("El loan es:"+loanToCreate);
            return ResponseEntity.ok(loanToCreate);



    }
/// ///////// Show all loans
    @GetMapping()
        public ResponseEntity<List<LoanApplication>> getAllLoans() {
            boolean isAdmin = isAdmin();
            if(!isAdmin) {
                String mail = getMail();
                List<LoanApplication> loanApplication = loanApplicationService.getLoansByMail(mail);
                return ResponseEntity.ok(loanApplication);
            }
            List<LoanApplication> loanApplication = loanApplicationService.getAllLoans();
            return ResponseEntity.ok(loanApplication);
        }
/// //////Show loan ByID

@PutMapping({"/id/{id}"})
public ResponseEntity<LoanApplication> getLoanById(@PathVariable Long id) {
    boolean isAdmin = isAdmin();
    if(!isAdmin) {
        String mail = getMail();
        LoanApplication loanApplication = loanApplicationService.getLoanByIdAndUser(id, mail);
        return ResponseEntity.ok(loanApplication);
    }
    LoanApplication loanApplication = loanApplicationService.getLoanById(id);
    return ResponseEntity.ok(loanApplication);
}

    @GetMapping({"/id/{id}/Approve"})
    public ResponseEntity<?> approveLoan(@PathVariable Long id) {
        boolean isAdmin = isAdmin();
        if(!isAdmin) {
            return ResponseEntity.status(401).body("You are not allowed to do this action");
        }
        LoanApplication loanApplication = loanApplicationService.approveLoan(id);
        return ResponseEntity.ok(loanApplication);
    }

    @GetMapping({"/id/{id}/Reject"})
    public ResponseEntity<?> rejectLoan(@PathVariable Long id) {
        boolean isAdmin = isAdmin();
        if(!isAdmin) {
            return ResponseEntity.status(401).body("You are not allowed to do this action");
        }
        LoanApplication loanApplication = loanApplicationService.rejectLoan(id);
        return ResponseEntity.ok(loanApplication);
    }

//    boolean isAdmin = isAdmin();
//        if(!isAdmin) return ResponseEntity.status(403).build();
//    Account target = accountService.getAccountById(id);
//        return ResponseEntity.ok(target);
}



