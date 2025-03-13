package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Account;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private List<Account> accounts = new ArrayList<>();

    @GetMapping
    public List<Account> getAllAccounts() {
        return accounts;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        accounts.add(account);
        return account;
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}