package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    // check if an account exists by username
    boolean existsByUsername(String username);
    //find account by username
    Account findByUsername(String username);
}
