package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.naming.AuthenticationException; 


@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be over 4 characters");
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        accountRepository.save(account);
    }
    
    public Account login(String username, String password) throws AuthenticationException {
        List<Account> accounts = accountRepository.findAll();
        for (Account acc : accounts) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                int acc_id = acc.getAccountId();
                return new Account (acc_id, username, password);
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }
}
