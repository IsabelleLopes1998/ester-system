package com.br.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaResponseDTO {
	private UUID id;
	private UUID idPagamento;
	private UUID idUsuario;
	private UUID idCliente;
	private LocalDate dataVenda;
}
