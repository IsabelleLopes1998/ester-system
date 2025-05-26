package com.br.demo.enums;

import com.br.demo.model.Produto;
import com.br.demo.service.movimentacao.EntradaStrategy;
import com.br.demo.service.movimentacao.MovimentacaoStrategy;
import com.br.demo.service.movimentacao.SaidaStrategy;

public enum TipoMovimentacao {
	ENTRADA(new EntradaStrategy()),
	SAIDA(new SaidaStrategy()),
	ENTRADA_MANUAL(new EntradaStrategy()),
	SAIDA_MANUAL(new SaidaStrategy());

	private final MovimentacaoStrategy strategy;

	TipoMovimentacao(MovimentacaoStrategy strategy) {
		this.strategy = strategy;
	}

	public void aplicar(Produto produto, int quantidade) {
		strategy.atualizarEstoque(produto, quantidade);
	}
}