package com.br.demo.model;

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
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(name = "id_pagamento", nullable = false)
	private Pagamento pagamento;
}