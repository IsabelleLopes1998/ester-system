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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true, length = 120)
    private String login;

    @Column(nullable = false, length = 60)
    private String senha;

    @Column(nullable = false, length = 15)
    private String telefonePrincipal;

    @Column(length = 15)
    private String telefoneSecundario;

    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;
}
