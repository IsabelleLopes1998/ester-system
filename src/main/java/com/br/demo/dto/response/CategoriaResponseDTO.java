package com.br.demo.dto.response;

public class CategoriaResponseDTO {
    private Long id;
    private String nome;
    private String descricao;

    public CategoriaResponseDTO() {
    }

    public CategoriaResponseDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }


    public Long getId() {
        return id;
    }


    public String getDescricao() {
        return descricao;
    }


    public String getNome() {
        return nome;
    }


}
