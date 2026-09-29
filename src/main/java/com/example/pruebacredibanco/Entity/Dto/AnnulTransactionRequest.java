package com.example.pruebacredibanco.Entity.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnnulTransactionRequest {
    @NotBlank(message = "El cardId es obligatorio")
    private String cardId;

    @NotNull(message = "El transactionId es obligatorio")
    private Long transactionId;
}