package com.br.demo.dto.response;

import com.br.demo.enums.TipoMovimentacao;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEstoqueResponseDTO {
	private UUID id;
	private String produtoName;
	private LocalDateTime data;
	private Integer quantidade;
	private String observacao;
	private TipoMovimentacao tipoMovimentacao;
	private UUID idUsuario;
	private UUID idCompra;
	private UUID idVenda;
}