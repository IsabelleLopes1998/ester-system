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
    private UUID id;
    private String nome;
    private String cpf;
    private LocalDate dateBirth;
    private String email;
    private String rua;
    private String numero;
    private String complemento;
    private String cep;
}