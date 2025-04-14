package com.br.demo.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaRequestDTO {
	private LocalDate data;
	private UUID idUsuario;
	private UUID idCliente;
	private UUID idPagamento;
}