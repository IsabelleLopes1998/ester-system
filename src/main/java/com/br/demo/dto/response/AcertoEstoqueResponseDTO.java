package com.br.demo.dto.response;

import com.br.demo.enums.TipoAcerto;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcertoEstoqueResponseDTO {
	private UUID id;
	private LocalDate data;
	private TipoAcerto tipoAcerto;
	private String motivo;
	private UUID idUsuario;
}