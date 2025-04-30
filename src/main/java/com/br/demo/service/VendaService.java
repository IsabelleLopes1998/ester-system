package com.br.demo.service;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.request.VendaRequestDTO;
import com.br.demo.dto.response.VendaResponseDTO;
import com.br.demo.enums.TipoMovimentacao;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendaService {
	
	private final VendaRepository vendaRepository;
	private final PagamentoRepository pagamentoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;
	private final VendaItemRepository vendaItemRepository;
	private final HistoricoValorRepository historicoValorRepository;
	private final MovimentacaoEstoqueService movimentacaoEstoqueService;
	private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

	public VendaService(
			VendaRepository vendaRepository,
			VendaItemRepository vendaItemRepository,
			PagamentoRepository pagamentoRepository ,
			ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository,
			HistoricoValorRepository historicoValorRepository,
			MovimentacaoEstoqueService movimentacaoEstoqueService,
			MovimentacaoEstoqueRepository movimentacaoEstoqueRepository
	) {
		this.vendaRepository = vendaRepository;
		this.pagamentoRepository = pagamentoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
		this.vendaItemRepository = vendaItemRepository;
		this.historicoValorRepository = historicoValorRepository;
		this.movimentacaoEstoqueService = movimentacaoEstoqueService;
		this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
	}
	
	public List<VendaResponseDTO> getAllVendas() {
		return vendaRepository.findAll().stream()
					   .map(this::toResponseDTO)
					   .collect(Collectors.toList());
	}
	
	public VendaResponseDTO getById(UUID id) {
		return vendaRepository.findById(id)
					   .map(this::toResponseDTO)
					   .orElse(null);
	}
	//TODO: Temos que por a logica de pagamento aqui.
	@Transactional
	public VendaResponseDTO createVenda(VendaRequestDTO dto, Usuario usuario) {
		Cliente cliente = clienteRepository.findById(dto.getIdCliente())
		.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + dto.getIdCliente()));
		Pagamento pagamento = pagamentoRepository.findById(dto.getIdPagamento()).orElse(null);

		if (usuario == null || cliente == null || pagamento == null) {
			throw new IllegalArgumentException("Usuário, cliente ou pagamento não encontrados.");
		}

		for (VendaItemDTO vendaItem : dto.getVendaItemList()) {
			Produto produto = produtoRepository.findById(vendaItem.getProdutoId())
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + vendaItem.getProdutoId()));

			if (produto.getQuantidadeEstoque() < vendaItem.getQuantidadeVenda()) {
				throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
			}
		}

		Venda venda = Venda.builder()
				.data(dto.getData())
				.usuario(usuario)
				.cliente(cliente)
				.pagamento(pagamento)
				.build();

		venda = vendaRepository.save(venda);

		List<VendaItem> vendaItens = new ArrayList<>();

		for (VendaItemDTO vendaItemDTO : dto.getVendaItemList()) {
			Produto produto = produtoRepository.findById(vendaItemDTO.getProdutoId())
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + vendaItemDTO.getProdutoId()));

			MovimentacaoEstoqueRequestDTO novaMovimentacao = new MovimentacaoEstoqueRequestDTO(
					vendaItemDTO.getProdutoId(),
					dto.getData(),
					vendaItemDTO.getQuantidadeVenda(),
					"VENDA",
					"SAIDA",
					null,
					venda.getId()
			);

			movimentacaoEstoqueService.criar(novaMovimentacao, usuario);
			produtoRepository.save(produto);

			HistoricoValor precoVigente = historicoValorRepository.findPrecoVigenteByProdutoAndData(
					produto.getId(), dto.getData()
			).orElseThrow(() -> new IllegalArgumentException("Preço não encontrado para produto " + produto.getId()));

			VendaItem vendaItem = VendaItem.builder()
					.id(new VendaItemId(venda.getId(), produto.getId()))
					.venda(venda)
					.produto(produto)
					.quantidade(vendaItemDTO.getQuantidadeVenda())
					.preçoUnitario(precoVigente.getPreçoUnitario())
					.build();

			vendaItens.add(vendaItem);
		}

		venda.setVendaItens(vendaItens);

		return toResponseDTO(venda);
	}
	//TODO: se quiser adicionar o delete logico para ficar tudo registrado, seria aqui.
	@Transactional
	public VendaResponseDTO updateVenda(UUID id, VendaRequestDTO dto) {
		Venda venda = vendaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Venda não encontrada: " + id));

		return toResponseDTO(venda);
	}

	@Transactional
	public void deleteVenda(UUID id) {
		Venda venda = vendaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Venda não encontrada: " + id));

		for (VendaItem item : venda.getVendaItens()) {
			Produto produto = item.getProduto();
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
			produtoRepository.save(produto);
		}

		List<MovimentacaoEstoque> movimentacoes = movimentacaoEstoqueRepository.findByVendaId(venda.getId());
		movimentacaoEstoqueRepository.deleteAll(movimentacoes);

		vendaRepository.delete(venda);
	}

	private VendaResponseDTO toResponseDTO(Venda venda) {
		return VendaResponseDTO.builder()
				.id(venda.getId())
				.usernameUsuario(venda.getUsuario().getUsername())
				.idCliente(venda.getCliente().getId())
				.idPagamento(venda.getPagamento().getId())
				.dataVenda(venda.getData())
				.vendaItemList(
						venda.getVendaItens() != null ?
								venda.getVendaItens().stream()
										.map(vendaItem -> VendaItemDTO.builder()
												.produtoId(vendaItem.getProduto().getId())
												.quantidadeVenda(vendaItem.getQuantidade())
												.build())
										.collect(Collectors.toList())
								: null
				)
				.valorTotal(venda.getValorTotal())
				.build();
	}
}
