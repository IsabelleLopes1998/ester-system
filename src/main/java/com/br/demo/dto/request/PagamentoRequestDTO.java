package com.br.demo.dto.request;

import com.br.demo.enums.FormaPagamento;
import com.br.demo.enums.StatusPagamento;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoRequestDTO {
    private FormaPagamento formaPagamento;
    private BigDecimal valorTotal;
    private StatusPagamento statusPagamento;
}
