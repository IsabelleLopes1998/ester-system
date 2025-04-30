package com.br.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private LocalDate data;

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VendaItem> vendaItens;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "id_pagamento", nullable = false)
	private Pagamento pagamento;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public BigDecimal getValorTotal() {
		return vendaItens.stream()
				.map(VendaItem::getValorTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}