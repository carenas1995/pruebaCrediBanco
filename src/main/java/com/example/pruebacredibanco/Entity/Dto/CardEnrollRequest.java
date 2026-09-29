package com.example.pruebacredibanco.Entity.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CardEnrollRequest {
    @NotBlank(message = "El cardId es obligatorio")
    private String cardId;
}