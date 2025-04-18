package com.br.demo.model;

import com.br.demo.enums.TipoAcerto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AcertoEstoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAcerto tipoAcerto;
	
	@Column(length = 200)
	private String motivo;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
}