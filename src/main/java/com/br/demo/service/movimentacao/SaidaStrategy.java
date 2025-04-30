package com.br.demo.service.movimentacao;

import com.br.demo.model.Produto;

public class SaidaStrategy implements MovimentacaoStrategy {

    public void atualizarEstoque(Produto produto, int quantidade) {
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
    }
}


