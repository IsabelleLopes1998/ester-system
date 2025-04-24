package com.br.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoValorResponseDTO {
    private UUID id;
    private LocalDate data;
    private BigDecimal valor;
    private String nome;
}
