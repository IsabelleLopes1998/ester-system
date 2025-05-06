package com.br.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cliente", schema = "public")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Nome é obrigatório.")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @Column(nullable = false, unique = true)
    @NotNull(message = "CPF ou CNPJ é obrigatório.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CPF ou CNPJ deve seguir o formato XXX.XXX.XXX-XX ou XX.XXX.XXX/XXXX-XX.")
    private String cpf;

    @Column(nullable = false)
    @NotNull(message = "Data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser no passado.")
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    @NotNull(message = "E-mail é obrigatório.")
    @Email(message = "E-mail deve ser válido.")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Rua é obrigatória.")
    @Size(min = 5, max = 200, message = "Rua deve ter entre 5 e 200 caracteres.")
    private String rua;

    @Column(nullable = false)
    @NotNull(message = "Número é obrigatório.")
    @Size(min = 1, max = 10, message = "Número deve ter entre 1 e 10 caracteres.")
    private String numero;

    @Column(nullable = false)
    @NotNull(message = "Complemento é obrigatório.")
    @Size(max = 100, message = "Complemento não pode ter mais de 100 caracteres.")
    private String complemento;

    @Column(nullable = false)
    @NotNull(message = "CEP é obrigatório.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve seguir o formato XXXXX-XXX.")
    private String cep;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "primeiro_telefone", length = 15)
    @Pattern(regexp = "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}", message = "Telefone deve seguir o formato (XX) 9XXXX-XXXX")
    private String primeiroTelefone;

    @Column(name = "segundo_telefone", length = 15)
    @Pattern(regexp = "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}", message = "Telefone deve seguir o formato (XX) 9XXXX-XXXX")
    private String segundoTelefone;

}
