package com.br.demo.dto.request;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VendaItemRequestDTO {
	private UUID produtoId;
	private Integer quantidadeVenda;
}