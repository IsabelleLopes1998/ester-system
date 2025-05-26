package com.br.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VendaItemDTO{
	private UUID produtoId;
	private Integer quantidadeVenda;
}