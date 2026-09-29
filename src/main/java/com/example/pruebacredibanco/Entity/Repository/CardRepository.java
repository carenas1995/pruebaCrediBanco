package com.example.pruebacredibanco.Entity.Repository;

import com.example.pruebacredibanco.Entity.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}