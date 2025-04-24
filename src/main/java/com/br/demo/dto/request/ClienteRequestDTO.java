package com.br.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteRequestDTO {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String rua;
    private String numero;
    private String complemento;
    private String cep;
}