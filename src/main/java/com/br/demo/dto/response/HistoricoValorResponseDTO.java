package com.br.demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistoricoValorResponseDTO {
    private Long id;
    private LocalDate data;
    private BigDecimal valor;
    private String nomeProduto;

    public HistoricoValorResponseDTO(){}


    public HistoricoValorResponseDTO(Long id, LocalDate data, BigDecimal valor, String nomeProduto) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.nomeProduto = nomeProduto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
}
