package com.example.pruebacredibanco.Services;

import com.example.pruebacredibanco.Entity.Dto.CardBalanceRequest;
import com.example.pruebacredibanco.Entity.Dto.CardEnrollRequest;
import com.example.pruebacredibanco.Entity.Repository.CardRepository;
import com.example.pruebacredibanco.Entity.Models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public Card generateCard(String productId, String cardholderName) {
        if (productId == null || productId.length() != 6) {
            throw new IllegalArgumentException("El productId debe tener exactamente 6 dígitos");
        }

        Random random = new Random();
        StringBuilder randomDigits = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            randomDigits.append(random.nextInt(10));
        }

        String fullCardId = productId + randomDigits.toString();
        String expirationDate = LocalDate.now().plusYears(3).format(DateTimeFormatter.ofPattern("MM/yyyy"));

        Card newCard = Card.builder()
                .cardId(fullCardId)
                .productId(productId)
                .cardholderName(cardholderName)
                .expirationDate(expirationDate)
                .balance(BigDecimal.ZERO)
                .isActive(false)
                .isBlocked(false)
                .build();

        return cardRepository.save(newCard);
    }

    @Transactional
    public Card enrollCard(CardEnrollRequest request) {
        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
        card.setIsActive(true);
        return cardRepository.save(card);
    }

    @Transactional
    public Card blockCard(String cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
        card.setIsBlocked(true);
        return cardRepository.save(card);
    }

    @Transactional
    public Card addBalance(CardBalanceRequest request) {
        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        if (!card.getIsActive()) {
            throw new RuntimeException("La tarjeta no está activa");
        }
        if (card.getIsBlocked()) {
            throw new RuntimeException("La tarjeta se encuentra bloqueada");
        }

        card.setBalance(card.getBalance().add(request.getBalance()));
        return cardRepository.save(card);
    }

    public BigDecimal getBalance(String cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
        return card.getBalance();
    }
}