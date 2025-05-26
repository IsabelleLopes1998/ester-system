package com.br.demo.dto.request;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.enums.FormaPagamento;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaRequestDTO {
	private LocalDateTime data;
	private Long idCliente;
	private List<VendaItemDTO> vendaItemList;
	private FormaPagamento formaPagamento;
}

