package com.example.pruebacredibanco.Entity.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardBalanceRequest {
    @NotBlank(message = "El cardId es obligatorio")
    private String cardId;

    @NotNull(message = "El saldo es obligatorio")
    @Min(value = 1, message = "La recarga debe ser mayor a 0")
    private BigDecimal balance;
}