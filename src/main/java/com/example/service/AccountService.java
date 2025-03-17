package com.example.service;

import com.example.repository.AccountRepository;

public class AccountService {

    private final AccountRepository accountRepository;

    // register account
    public Account register(Account account){
        return AccountRepository.save(account);
    }
}
