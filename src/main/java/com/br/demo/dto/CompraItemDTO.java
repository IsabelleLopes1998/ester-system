package com.br.demo.dto;

import lombok.*;

import java.util.UUID;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompraItemDTO {
    private UUID produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
}
