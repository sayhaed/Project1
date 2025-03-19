package com.reveture.Project1.controller;

import com.reveture.Project1.dto.AccountDTO;
import com.reveture.Project1.entity.Account;
import com.reveture.Project1.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    //@PreAuthorize("hasRole('Manager')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }


    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@Valid @RequestBody AccountDTO accountDTO) {
        Account newAccount = accountService.registerAccount(accountDTO);
        return ResponseEntity.ok(newAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    // Obtener usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable String email) {
        Account account = accountService.getAccountByEmail(email);
        return ResponseEntity.ok(account);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
        Account updatedAccount = accountService.updateAccount(id, accountDTO);
        return ResponseEntity.ok(updatedAccount);
    }



}
