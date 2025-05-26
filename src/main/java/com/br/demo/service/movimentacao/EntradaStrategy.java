package com.br.demo.service.movimentacao;

import com.br.demo.model.Produto;
import com.br.demo.service.movimentacao.MovimentacaoStrategy;

public class EntradaStrategy implements MovimentacaoStrategy {
    public void atualizarEstoque(Produto produto, int quantidade) {
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
    }
}


