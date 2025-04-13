package com.br.demo.model;

import com.br.demo.model.Pagamento;
import com.br.demo.model.Usuario;
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
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate data;

    @Column
    private String fornecedor;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_pagamento", nullable = false)
    private Pagamento pagamento;

    @Column(nullable = false)
    private Integer quantidadeParcelas;
}
