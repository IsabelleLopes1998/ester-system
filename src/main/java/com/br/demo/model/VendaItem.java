package com.br.demo.model;

import com.br.demo.enums.StatusVenda;
import com.br.demo.enums.StatusVendaItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
	@Min(value = 1, message = "Quantidade deve ser no mínimo 1.")
	private Integer quantidade;

	@Column(nullable = false, precision = 10, scale = 2)
	@DecimalMin(value = "0.01", message = "O preço não pode ser menor que 0.01.")
	private BigDecimal preçoUnitario;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusVendaItem statusVendaItem;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	/**
	 * Método que calcula o valor total do item de venda
	 * @return o valor total da venda item (quantidade * preço unitário)
	 */
	public BigDecimal getValorTotal() {
		return this.preçoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
	}
}
