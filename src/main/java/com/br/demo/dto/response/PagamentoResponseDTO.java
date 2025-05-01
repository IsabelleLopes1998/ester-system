package com.br.demo.dto.response;

import com.br.demo.enums.FormaPagamento;
import com.br.demo.enums.StatusPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoResponseDTO {
    private UUID id;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
    private BigDecimal valorTotal;
}
