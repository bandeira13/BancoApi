package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Card;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private List<Card> cards = new ArrayList<>();

    // Endpoint para listar todos os cartões
    @GetMapping
    public List<Card> getAllCards() {
        return cards;
    }

    // Endpoint para criar um novo cartão
    @PostMapping
    public Card createCard(@RequestBody Card card) {
        cards.add(card);
        return card;
    }

    // Endpoint para buscar um cartão pelo ID
    @GetMapping("/{id}")
    public Card getCardById(@PathVariable Long id) {
        return cards.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}