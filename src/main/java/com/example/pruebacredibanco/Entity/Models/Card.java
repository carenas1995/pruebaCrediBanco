package com.example.pruebacredibanco.Entity.Models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @Column(name = "card_id", length = 16)
    private String cardId;

    @Column(name = "product_id", nullable = false, length = 6)
    private String productId;

    @Column(name = "cardholder_name", length = 100)
    private String cardholderName;

    @Column(name = "expiration_date", nullable = false, length = 7)
    private String expirationDate;

    @Builder.Default
    @Column(precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = false;

    @Builder.Default
    @Column(name = "is_blocked")
    private Boolean isBlocked = false;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}