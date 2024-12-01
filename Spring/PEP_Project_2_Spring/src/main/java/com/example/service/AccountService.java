package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createNewUserAccount(Account account) {
        if (account.getUsername().length() == 0)
            return null;
        if (account.getPassword().length() < 4)
            return null;

        List<Account> allAccounts = accountRepository.findAll();
        for (Account acc : allAccounts) {
            if (acc.getUsername().compareTo(account.getUsername()) == 0)
                return null;
        }

        return accountRepository.save(account);
    }

    public Account userLogin(Account account) {
        List<Account> allAccounts = accountRepository.findAll();

        for (Account acc : allAccounts) {
            boolean areUsernamesEqual = acc.getUsername().compareTo(account.getUsername()) == 0;
            boolean arePasswordsEqual = acc.getPassword().compareTo(account.getPassword()) == 0;
            if (areUsernamesEqual && arePasswordsEqual)
                return acc;
        }

        return null;
    }
}