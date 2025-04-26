package com.br.demo.model;

import com.br.demo.enums.TipoAcerto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AcertoItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@MapsId("idProduto")
	@JoinColumn(name = "id_produto", nullable = false)
	private Produto produto;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal valor;
	
	@Column(length = 200)
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAcerto tipoAcerto;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
}