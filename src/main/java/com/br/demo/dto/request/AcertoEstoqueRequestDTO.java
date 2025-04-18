package com.br.demo.dto.request;

import com.br.demo.enums.TipoAcerto;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcertoEstoqueRequestDTO {
	private LocalDate data;
	private TipoAcerto tipoAcerto;
	private String motivo;
	private UUID idUsuario;
}