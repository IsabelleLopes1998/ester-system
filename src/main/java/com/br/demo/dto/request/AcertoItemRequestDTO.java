package com.br.demo.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcertoItemRequestDTO {
	private UUID idProduto;
	private UUID idAcerto;
	private LocalDate data;
	private Integer quantidade;
	private BigDecimal valor;
	private String observacao;
}