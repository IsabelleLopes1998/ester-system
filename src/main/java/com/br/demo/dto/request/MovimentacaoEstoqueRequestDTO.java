package com.br.demo.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEstoqueRequestDTO {
	private UUID idProduto;
	private LocalDate data;
	private Integer quantidade;
	private String observacao;
	private String tipoAcerto;
}