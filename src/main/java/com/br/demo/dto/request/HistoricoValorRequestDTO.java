package com.br.demo.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistoricoValorRequestDTO {
    private LocalDate data;
    private BigDecimal valor;
    private Long idProduto;

    public HistoricoValorRequestDTO(){}

    public Long getIdProduto() {
        return idProduto;
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

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }
}
