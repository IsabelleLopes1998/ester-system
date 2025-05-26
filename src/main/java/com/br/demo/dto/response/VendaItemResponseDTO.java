package com.br.demo.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VendaItemResponseDTO {
	private String nomeProduto;
	private Integer quantidadeVenda;
}