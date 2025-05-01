package com.br.demo.dto.request;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.enums.FormaPagamento;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaRequestDTO {
	private LocalDate data;
	private Long idCliente;
	private List<VendaItemDTO> vendaItemList;
	private FormaPagamento formaPagamento;
}

