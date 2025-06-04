package com.br.demo.dto.request;


import com.br.demo.dto.CompraItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraRequestDTO {
    private LocalDateTime data;
    private String fornecedor;
    private List<CompraItemDTO> itens;
    private BigDecimal valorTotalDaCompra;;
}
