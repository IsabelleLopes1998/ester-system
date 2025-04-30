package com.br.demo.service.movimentacao;

import com.br.demo.model.Produto;

public interface MovimentacaoStrategy {
    void atualizarEstoque(Produto produto, int quantidade);
}
