package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final List<User> users = new ArrayList<>();

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    // Endpoint para criar um novo usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Validação básica dos campos
        if (user.name() == null || user.name().isBlank() || user.email() == null || user.email().isBlank()) {
            return ResponseEntity.badRequest().build(); // Retorna 400 se os campos forem inválidos
        }

        // Gera um ID único para o novo usuário
        User newUser = new User(UUID.randomUUID().toString(), user.name(), user.email());
        users.add(newUser); // Adiciona o usuário à lista
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser); // Retorna 201 com o usuário criado
    }

    // Endpoint para buscar um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        // Procura o usuário na lista pelo ID
        User user = users.stream()
                .filter(u -> u.id().equals(id)) // Usa o método id() do record
                .findFirst()
                .orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        }

        return ResponseEntity.ok(user); // Retorna 200 com o usuário encontrado
    }
}