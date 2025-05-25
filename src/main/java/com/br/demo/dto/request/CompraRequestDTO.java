package com.br.demo.dto.request;


import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraRequestDTO {
    private LocalDate data;
    private String fornecedor;
    private List<CompraItemRequestDTO> itens;
}
