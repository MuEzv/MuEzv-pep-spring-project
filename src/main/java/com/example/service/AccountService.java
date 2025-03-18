package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // register account
    /* 1: new User registrations.
    The registration will be successful if and only if 
    - the username is not blank, 
    - the password is at least 4 characters long, and 
    - an Account with that username does not already exist. 
    */
    public Account register(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("Username cannot be blank");
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
        return accountRepository.save(createdAccount);
    }

    /*
     * User Login
     * The login will be successful if and only if 
     * the username and password provided in the request body JSON match a real account existing on the database. 
     * 
     */
    public Account Login(Account account){
        Account trueAccount = accountRepository.findByUsername(account.getUsername());
        if(trueAccount != null && trueAccount.getPassword().equals(account.getPassword())){
            return trueAccount;
        }
        throw new IllegalArgumentException("The passowrd doesn't match");
    } 

}
