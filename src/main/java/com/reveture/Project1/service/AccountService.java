package com.reveture.Project1.service;

import com.reveture.Project1.dto.AccountDTO;
import com.reveture.Project1.entity.Account;

import java.util.List;

public interface AccountService {
    Account registerAccount(AccountDTO accountDTO);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    Account getAccountByEmail(String email);
    Account updateAccount(Long id, AccountDTO accountDTO);
}
