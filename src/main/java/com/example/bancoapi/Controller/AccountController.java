package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Account;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    // Lista temporária para simular um banco de dados
    private final List<Account> accounts = new ArrayList<>();

    // Endpoint para criar uma nova conta
    @PostMapping
    public String createAccount(@RequestParam long id, @RequestParam String accountNumber, @RequestParam Double balance) {
        Account newAccount = new Account(id, accountNumber, balance);
        accounts.add(newAccount);
        return "Conta criada com sucesso! Número da conta: " + newAccount.accountNumber();
    }

    // Endpoint para listar todas as contas
    @GetMapping
    public List<Account> getAllAccounts() {
        return accounts;
    }

    // Endpoint para buscar uma conta pelo ID
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable long id) {
        return accounts.stream()
                .filter(account -> account.id() == id)
                .findFirst()
                .orElse(null); // Retorna null se não encontrar a conta
    }
}