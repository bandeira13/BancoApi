package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    // Lista temporária para simular um banco de dados
    private final List<Card> cards = new ArrayList<>();

    // Endpoint para criar um novo cartão
    @PostMapping
    public ResponseEntity<String> createCard(@RequestParam String cardNumber, @RequestParam String cardType) {
        // Validação básica dos campos
        if (cardNumber == null || cardNumber.isBlank() || cardType == null || cardType.isBlank()) {
            return ResponseEntity.badRequest().body("Número do cartão e tipo são obrigatórios!");
        }

        // Gera um ID único para o novo cartão
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE; // Gera um ID positivo
        Card newCard = new Card(id, cardNumber, cardType);
        cards.add(newCard);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cartão criado com sucesso! Número do cartão: " + newCard.cardNumber());
    }

    // Endpoint para listar todos os cartões
    @GetMapping
    public List<Card> getAllCards() {
        return cards;
    }

    // Endpoint para buscar um cartão pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        Card card = cards.stream()
                .filter(c -> c.id().equals(id)) // Usa o método id() do record
                .findFirst()
                .orElse(null);

        if (card == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o cartão não for encontrado
        }

        return ResponseEntity.ok(card); // Retorna 200 com o cartão encontrado
    }

    // Endpoint para atualizar o tipo de um cartão
    @PutMapping("/{id}/type")
    public ResponseEntity<String> updateCardType(@PathVariable Long id, @RequestParam String newCardType) {
        Card card = cards.stream()
                .filter(c -> c.id().equals(id))
                .findFirst()
                .orElse(null);

        if (card == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o cartão não for encontrado
        }

        // Cria um novo cartão com o tipo atualizado
        Card updatedCard = new Card(card.id(), card.cardNumber(), newCardType);
        cards.remove(card); // Remove o cartão antigo
        cards.add(updatedCard); // Adiciona o cartão atualizado

        return ResponseEntity.ok("Tipo do cartão " + updatedCard.cardNumber() + " atualizado para: " + updatedCard.cardType());
    }

    // Endpoint para deletar um cartão
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable Long id) {
        Card card = cards.stream()
                .filter(c -> c.id().equals(id))
                .findFirst()
                .orElse(null);

        if (card == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o cartão não for encontrado
        }

        cards.remove(card); // Remove o cartão da lista
        return ResponseEntity.ok("Cartão " + card.cardNumber() + " removido com sucesso!");
    }
}