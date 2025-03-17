package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

public class AccountService {

    private final AccountRepository accountRepository;

    // register account
    public Account register(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("Username cannoy be blank");
        }
        
        if(password == null || password.length() < 4){
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        if(accountRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already exists");
        }

        Account createdAccount = new Account();
        createdAccount.setUsername(username);
        createdAccount.setPassword(password);
        return AccountRepository.save(createdAccount);
    }
}
