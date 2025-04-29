package com.br.demo.dto.response;

import com.br.demo.enums.TipoMovimentacao;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEstoqueResponseDTO {
	private UUID id;
	private UUID idProduto;
	private LocalDate data;
	private Integer quantidade;
	private String observacao;
	private TipoMovimentacao tipoMovimentacao;
	private UUID idUsuario;
}