package com.br.demo.dto.response;

import com.br.demo.enums.FormaPagamento;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoResponseDTO {
    private UUID id;
    private FormaPagamento formaPagamento;
}
