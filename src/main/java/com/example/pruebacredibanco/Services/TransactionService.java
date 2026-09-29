package com.example.pruebacredibanco.Services;

import com.example.pruebacredibanco.Entity.Dto.AnnulTransactionRequest;
import com.example.pruebacredibanco.Entity.Dto.PurchaseRequest;
import com.example.pruebacredibanco.Entity.Repository.CardRepository;
import com.example.pruebacredibanco.Entity.Repository.TransactionRepository;
import com.example.pruebacredibanco.Entity.Models.Card;
import com.example.pruebacredibanco.Entity.Models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public Transaction processPurchase(PurchaseRequest request) {
        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        if (!card.getIsActive()) {
            throw new RuntimeException("La tarjeta no está activa");
        }
        if (card.getIsBlocked()) {
            throw new RuntimeException("La tarjeta está bloqueada");
        }
        if (card.getBalance().compareTo(request.getPrice()) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        card.setBalance(card.getBalance().subtract(request.getPrice()));
        cardRepository.save(card);

        Transaction transaction = Transaction.builder()
                .card(card)
                .amount(request.getPrice())
                .status("APPROVED")
                .build();

        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    @Transactional
    public Transaction annulTransaction(AnnulTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        if (!transaction.getCard().getCardId().equals(request.getCardId())) {
            throw new RuntimeException("La transacción no pertenece a la tarjeta indicada");
        }

        if (!"APPROVED".equals(transaction.getStatus())) {
            throw new RuntimeException("Solo se pueden anular transacciones aprobadas");
        }

        if (transaction.getCreatedAt().isBefore(LocalDateTime.now().minusHours(24))) {
            throw new RuntimeException("Ha superado el límite de 24 horas para anular la transacción");
        }

        Card card = transaction.getCard();
        card.setBalance(card.getBalance().add(transaction.getAmount()));
        cardRepository.save(card);

        transaction.setStatus("CANCELLED");
        return transactionRepository.save(transaction);
    }
}