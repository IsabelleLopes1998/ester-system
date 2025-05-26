package com.br.demo.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraItemResponseDTO {
    private String nomeProduto;
    private BigDecimal valorUnitario;
    private Integer quantidadeVenda;
}
