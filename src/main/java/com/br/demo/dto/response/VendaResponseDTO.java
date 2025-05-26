package com.br.demo.dto.response;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.enums.StatusVenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaResponseDTO {
	private UUID id;
	private UUID idPagamento;
	private String usernameUsuario;
	private Long idCliente;
	private LocalDateTime dataVenda;
	private List<VendaItemDTO> vendaItemList;
	private BigDecimal valorTotal;
	private StatusVenda statusVenda;
}
