package com.reveture.Project1.service;

import com.reveture.Project1.dto.AccountDTO;
import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.AccountType;
import com.reveture.Project1.repository.AccountRepository;
import com.reveture.Project1.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Account registerAccount(AccountDTO accountDTO) {
        // Validar si el email ya existe
        if (accountRepository.existsByEmail(accountDTO.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Obtener el tipo de cuenta
        Optional<AccountType> accountTypeOpt = accountTypeRepository.findById(accountDTO.getAccountTypeId());
        if (accountTypeOpt.isEmpty()) {
            throw new RuntimeException("Invalid account type");
        }

        // Crear nueva cuenta
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword())); // Hashing de la contraseña
        account.setAccountType(accountTypeOpt.get());

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public Account updateAccount(Long id, AccountDTO accountDTO) {
        Account account = getAccountById(id); // Buscar usuario

        account.setEmail(accountDTO.getEmail()); // Actualizar email si se quiere cambiar
        if (accountDTO.getPassword() != null && !accountDTO.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(accountDTO.getPassword())); // Cifrar nueva contraseña
        }

        return accountRepository.save(account);
    }

}
