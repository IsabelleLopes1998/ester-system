package com.br.demo.service;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.VendaItemResponseDTO;
import com.br.demo.enums.StatusVendaItem;
import com.br.demo.model.*;
import com.br.demo.repository.HistoricoValorRepository;
import com.br.demo.repository.ProdutoRepository;
import com.br.demo.repository.VendaItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaItemService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private HistoricoValorRepository historicoValorRepository;

	@Autowired
	private VendaItemRepository vendaItemRepository;

	@Autowired
	private MovimentacaoEstoqueService movimentacaoEstoqueService;

	@Transactional
	public List<VendaItem> criarItensVenda(Venda venda, List<VendaItemDTO> vendaItemDTOs, LocalDateTime dataVenda) {
		List<VendaItem> vendaItens = new ArrayList<>();

		for (VendaItemDTO vendaItemDTO : vendaItemDTOs) {
			Produto produto = produtoRepository.findById(vendaItemDTO.getProdutoId())
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + vendaItemDTO.getProdutoId()));

			if (produto.getQuantidadeEstoque() < vendaItemDTO.getQuantidadeVenda()) {
				throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
			}

			HistoricoValor precoVigente = historicoValorRepository.findPrecoVigenteByProdutoAndData(
					produto.getId(), dataVenda
			).orElseThrow(() -> new IllegalArgumentException("Preço não encontrado para produto " + produto.getId()));

			VendaItem vendaItem = VendaItem.builder()
					.id(new VendaItemId(venda.getId(), produto.getId()))
					.venda(venda)
					.produto(produto)
					.quantidade(vendaItemDTO.getQuantidadeVenda())
					.preçoUnitario(precoVigente.getPrecoUnitario())
					.statusVendaItem(StatusVendaItem.AGUARDANDO_PAGAMENTO)
					.build();

			vendaItens.add(vendaItem);
		}

		return vendaItemRepository.saveAll(vendaItens);
	}
}