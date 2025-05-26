package com.br.demo.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEstoqueRequestDTO {
	private UUID idProduto;
	private LocalDateTime data;
	private Integer quantidade;
	private String observacao;
	private String tipoAcerto;
	private UUID idCompra;
	private UUID idVenda;
}