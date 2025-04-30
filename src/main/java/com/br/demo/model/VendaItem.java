package com.br.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VendaItem {
	
	@EmbeddedId
	private VendaItemId id;
	
	@ManyToOne
	@MapsId("produtoId")
	@JoinColumn(name = "id_produto", nullable = false)
	private Produto produto;
	
	@ManyToOne
	@MapsId("vendaId")
	@JoinColumn(name = "id_venda", nullable = false)
	private Venda venda;
	
	@Column(nullable = false)
	private Integer quantidade;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal preçoUnitario;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	protected BigDecimal getValorTotal() {
		return this.preçoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
	}
}