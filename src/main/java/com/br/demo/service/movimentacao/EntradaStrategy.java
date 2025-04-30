package com.br.demo.service.movimentacao;

import com.br.demo.model.Produto;
import com.br.demo.service.movimentacao.MovimentacaoStrategy;

public class EntradaStrategy implements MovimentacaoStrategy {
    public void atualizarEstoque(Produto produto, int quantidade) {
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
    }
}


