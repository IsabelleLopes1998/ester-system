package com.br.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String username;
    private String senha;
    private String telefonePrincipal;
    private String telefoneSecundario;
    private UUID idCargo;
}

