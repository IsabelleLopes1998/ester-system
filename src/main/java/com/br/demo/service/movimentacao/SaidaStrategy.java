package com.br.demo.service.movimentacao;

import com.br.demo.model.Produto;

public class SaidaStrategy implements MovimentacaoStrategy {
    public void atualizarEstoque(Produto produto, int quantidade) {
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
    }
}


