package com.br.demo.dto.request;


import lombok.*;

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
    private List<CompraItemRequestDTO> itens;
}
