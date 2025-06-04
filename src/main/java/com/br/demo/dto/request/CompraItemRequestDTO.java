package com.br.demo.dto.request;

import lombok.*;

import java.util.UUID;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraItemRequestDTO {
    private UUID produtoId;
    private BigDecimal valorUnitario;
    private Integer quantidadeVenda;



}
