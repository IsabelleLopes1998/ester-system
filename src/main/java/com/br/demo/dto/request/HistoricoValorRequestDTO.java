package com.br.demo.dto.request;

import com.br.demo.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoValorRequestDTO {
    private LocalDateTime data;
    private BigDecimal valor;
    private Produto produto;
}
