package com.example.pruebacredibanco.Controllers;

import com.example.pruebacredibanco.Entity.Dto.AnnulTransactionRequest;
import com.example.pruebacredibanco.Entity.Dto.PurchaseRequest;
import com.example.pruebacredibanco.Entity.Models.Transaction;
import com.example.pruebacredibanco.Services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<Transaction> purchase(@Valid @RequestBody PurchaseRequest request) {
        return ResponseEntity.ok(transactionService.processPurchase(request));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("transactionId") Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }

    @PostMapping("/anull")
    public ResponseEntity<Transaction> annulTransaction(@Valid @RequestBody AnnulTransactionRequest request) {
        return ResponseEntity.ok(transactionService.annulTransaction(request));
    }
}