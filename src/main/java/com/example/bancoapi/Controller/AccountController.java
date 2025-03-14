package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    // Lista temporária para simular um banco de dados
    private final List<Account> accounts = new ArrayList<>();

    // Endpoint para criar uma nova conta
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestParam String accountNumber, @RequestParam Double balance) {
        // Validação básica dos campos
        if (accountNumber == null || accountNumber.isBlank() || balance == null || balance < 0) {
            return ResponseEntity.badRequest().body("Número da conta e saldo são obrigatórios, e o saldo não pode ser negativo!");
        }

        // Gera um ID único para a nova conta
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE; // Gera um ID positivo
        Account newAccount = new Account(id, accountNumber, balance);
        accounts.add(newAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body("Conta criada com sucesso! Número da conta: " + newAccount.accountNumber());
    }

    // Endpoint para listar todas as contas
    @GetMapping
    public List<Account> getAllAccounts() {
        return accounts;
    }

    // Endpoint para buscar uma conta pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accounts.stream()
                .filter(a -> a.id().equals(id)) // Usa o método id() do record
                .findFirst()
                .orElse(null);

        if (account == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a conta não for encontrada
        }

        return ResponseEntity.ok(account); // Retorna 200 com a conta encontrada
    }

    // Endpoint para atualizar o saldo de uma conta
    @PutMapping("/{id}/balance")
    public ResponseEntity<String> updateAccountBalance(@PathVariable Long id, @RequestParam Double newBalance) {
        Account account = accounts.stream()
                .filter(a -> a.id().equals(id))
                .findFirst()
                .orElse(null);

        if (account == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a conta não for encontrada
        }

        // Cria uma nova conta com o saldo atualizado
        Account updatedAccount = new Account(account.id(), account.accountNumber(), newBalance);
        accounts.remove(account); // Remove a conta antiga
        accounts.add(updatedAccount); // Adiciona a conta atualizada

        return ResponseEntity.ok("Saldo da conta " + updatedAccount.accountNumber() + " atualizado para: " + updatedAccount.balance());
    }

    // Endpoint para deletar uma conta
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        Account account = accounts.stream()
                .filter(a -> a.id().equals(id))
                .findFirst()
                .orElse(null);

        if (account == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a conta não for encontrada
        }

        accounts.remove(account); // Remove a conta da lista
        return ResponseEntity.ok("Conta " + account.accountNumber() + " removida com sucesso!");
    }
}