package com.reveture.Project1.controller;

import com.reveture.Project1.dto.AccountDTO;
import com.reveture.Project1.entity.Account;
import com.reveture.Project1.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    private Boolean isAdmin(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Account requester = accountService.getAccountByEmail(email);
        //boolean isAdmin = requester.getAccountType().getType().equalsIgnoreCase("Admin");
        //return isAdmin;
        return "Admin".equalsIgnoreCase(requester.getAccountType().getType());
    }

    private Account getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.getAccountByEmail(email);
    }


    //@PreAuthorize("hasRole('Manager')")
    /// validamos el acceso de admin a este endpoint
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        boolean isAdmin = isAdmin();
        if(!isAdmin) return ResponseEntity.status(403).build();
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }


    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@Valid @RequestBody AccountDTO accountDTO) {
        Account newAccount = accountService.registerAccount(accountDTO);
        return ResponseEntity.ok(newAccount);
    }

    /// validamos el acceso de admin a este endpoint
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        boolean isAdmin = isAdmin();
        if(!isAdmin) return ResponseEntity.status(403).build();
        Account target = accountService.getAccountById(id);
        return ResponseEntity.ok(target);
    }

    //usuario logueado actualmente
    @GetMapping("/me")
    public ResponseEntity<Account> getMyAccount() {
        return ResponseEntity.ok(getCurrentUser());
    }


    // Obtener usuario por email
    /// validamos el acceso de admin a este endpoint
    @GetMapping("/email/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable String email) {
        boolean isAdmin = isAdmin();
        if(!isAdmin) return ResponseEntity.status(403).build();
        Account account = accountService.getAccountByEmail(email);
        return ResponseEntity.ok(account);
    }

    // Obtener usuario por email
//    @GetMapping("/email/me")
//    public ResponseEntity<Account> getAccountByEmail() {
//        Account account = getCurrentUser();
//        return ResponseEntity.ok(account);
//    }


    //usuario logueado actualmente
    @PutMapping("/me")
    public ResponseEntity<Account> updateMyAccount(@Valid @RequestBody AccountDTO accountDTO) {
        Account current = getCurrentUser();
        Account updated = accountService.updateAccount(current.getId(), accountDTO);
        return ResponseEntity.ok(updated);
    }


    // Actualizar usuario
    /// validamos el acceso de admin a este endpoint
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        Account updated = accountService.updateAccount(id, accountDTO);
        return ResponseEntity.ok(updated);
    }
}
