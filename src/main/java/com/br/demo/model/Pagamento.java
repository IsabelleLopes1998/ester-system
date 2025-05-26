package com.br.demo.model;

import com.br.demo.enums.FormaPagamento;
import com.br.demo.enums.StatusPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "Valor total é obrigatória.")
    @DecimalMin(value = "0.01", message = "O valor não pode ser menor que 0.01.")
    private BigDecimal valorTotal;

    @Column(nullable = false)
    @NotNull(message = "Data é obrigatória.")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status pagamento é obrigatório.")
    private StatusPagamento statusPagamento;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
