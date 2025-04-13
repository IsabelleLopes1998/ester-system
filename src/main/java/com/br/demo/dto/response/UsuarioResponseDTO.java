package com.br.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private UUID id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String login;
    private String telefonePrincipal;
    private String telefoneSecundario;
    private String cargoNome;
}
