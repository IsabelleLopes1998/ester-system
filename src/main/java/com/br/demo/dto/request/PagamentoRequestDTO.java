package com.br.demo.dto.request;

import com.br.demo.enums.FormaPagamento;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoRequestDTO {
    private FormaPagamento formaPagamento;
}
