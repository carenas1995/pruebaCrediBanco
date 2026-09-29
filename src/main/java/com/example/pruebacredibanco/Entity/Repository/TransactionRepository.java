package com.example.pruebacredibanco.Entity.Repository;

import com.example.pruebacredibanco.Entity.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}