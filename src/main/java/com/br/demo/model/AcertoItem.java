package com.br.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AcertoItem {
	
	@EmbeddedId
	private AcertoItemId id;
	
	@ManyToOne
	@MapsId("idProduto")
	@JoinColumn(name = "id_produto", nullable = false)
	private Produto produto;
	
	@ManyToOne
	@MapsId("idAcerto")
	@JoinColumn(name = "id_acerto", nullable = false)
	private AcertoEstoque acerto;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal valor;
	
	@Column(length = 200)
	private String observacao;
}