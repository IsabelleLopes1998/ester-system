package com.br.demo.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraResponseDTO {
    private UUID id;
    private LocalDateTime data;
    private String fornecedor;
    private String nomeUsuario;
    private List<CompraItemResponseDTO> itens;
}
