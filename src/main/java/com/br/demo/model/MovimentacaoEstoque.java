package com.br.demo.model;

import com.br.demo.enums.TipoAcerto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MovimentacaoEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false)
	@NotNull(message = "Produto é obrigatório.")
	private Produto produto;

	@Column(nullable = false)
	@NotNull(message = "Data é obrigatória.")
	private LocalDate data;

	@Column(nullable = false)
	@NotNull(message = "Quantidade é obrigatória.")
	@Min(value = 1, message = "Quantidade deve ser no mínimo 1.")
	private Integer quantidade;

	@Column(precision = 10, scale = 2)
	@NotNull(message = "Valor é obrigatório.")
	@DecimalMin(value = "0.01", message = "O valor não pode ser menor que 0.01.")
	private BigDecimal valor;

	@Column(length = 200)
	@Size(max = 200, message = "Observação pode ter no máximo 200 caracteres.")
	private String observacao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Tipo de acerto é obrigatório.")
	private TipoAcerto tipoAcerto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	@NotNull(message = "Usuário é obrigatório.")
	private Usuario usuario;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
