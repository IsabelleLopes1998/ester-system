package com.br.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String rua;
    private String numero;
    private String complemento;
    private String cep;
    private String primeiroTelefone;
    private String segundoTelefone;
}