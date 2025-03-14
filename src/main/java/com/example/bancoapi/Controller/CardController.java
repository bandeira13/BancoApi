package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Card;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    // Lista temporária para simular um banco de dados
    private final List<Card> cards = new ArrayList<>();

    // Endpoint para criar um novo cartão
    @PostMapping
    public String createCard(@RequestParam Long id, @RequestParam String cardNumber, @RequestParam String cardType) {
        Card newCard = new Card();
        newCard.setId(id);
        newCard.setCardNumber(cardNumber);
        newCard.setCardType(cardType);
        cards.add(newCard);
        return "Cartão criado com sucesso! Número do cartão: " + newCard.getCardNumber();
    }

    // Endpoint para listar todos os cartões
    @GetMapping
    public List<Card> getAllCards() {
        return cards;
    }

    // Endpoint para buscar um cartão pelo ID
    @GetMapping("/{id}")
    public Card getCardById(@PathVariable Long id) {
        return cards.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElse(null); // Retorna null se não encontrar o cartão
    }

    // Endpoint para atualizar o tipo de um cartão
    @PutMapping("/{id}/type")
    public String updateCardType(@PathVariable Long id, @RequestParam String newCardType) {
        Card card = getCardById(id);
        if (card != null) {
            card.setCardType(newCardType);
            return "Tipo do cartão " + card.getCardNumber() + " atualizado para: " + card.getCardType();
        } else {
            return "Cartão não encontrado!";
        }
    }

    // Endpoint para deletar um cartão
    @DeleteMapping("/{id}")
    public String deleteCard(@PathVariable Long id) {
        Card card = getCardById(id);
        if (card != null) {
            cards.remove(card);
            return "Cartão " + card.getCardNumber() + " removido com sucesso!";
        } else {
            return "Cartão não encontrado!";
        }
    }
}