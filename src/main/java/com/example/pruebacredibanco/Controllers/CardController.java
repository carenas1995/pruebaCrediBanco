package com.example.pruebacredibanco.Controllers;

import com.example.pruebacredibanco.Entity.Dto.CardBalanceRequest;
import com.example.pruebacredibanco.Entity.Dto.CardEnrollRequest;
import com.example.pruebacredibanco.Entity.Models.Card;
import com.example.pruebacredibanco.Services.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/{productId}/generate")
    public ResponseEntity<Card> generateCard(
            @PathVariable("productId") String productId,
            @RequestParam(value = "cardholderName", required = false, defaultValue = "Cliente CrediBanco") String cardholderName) {
        return ResponseEntity.ok(cardService.generateCard(productId, cardholderName));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Card> blockCard(@PathVariable("cardId") String cardId) {
        return ResponseEntity.ok(cardService.blockCard(cardId));
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable("cardId") String cardId) {
        BigDecimal balance = cardService.getBalance(cardId);
        return ResponseEntity.ok(Map.of("cardId", cardId, "balance", balance));
    }

    @PostMapping("/enroll")
    public ResponseEntity<Card> enrollCard(@Valid @RequestBody CardEnrollRequest request) {
        return ResponseEntity.ok(cardService.enrollCard(request));
    }

    @PostMapping("/balance")
    public ResponseEntity<Card> addBalance(@Valid @RequestBody CardBalanceRequest request) {
        return ResponseEntity.ok(cardService.addBalance(request));
    }
}