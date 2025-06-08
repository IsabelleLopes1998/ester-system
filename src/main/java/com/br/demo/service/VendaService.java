package com.br.demo.service;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.request.PagamentoRequestDTO;
import com.br.demo.dto.request.VendaRequestDTO;
import com.br.demo.dto.response.PagamentoResponseDTO;
import com.br.demo.dto.response.VendaItemResponseDTO;
import com.br.demo.dto.response.VendaResponseDTO;
import com.br.demo.enums.StatusPagamento;
import com.br.demo.enums.StatusVenda;
import com.br.demo.enums.StatusVendaItem;
import com.br.demo.enums.TipoMovimentacao;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	@Autowired
	private VendaItemService vendaItemService;
	@Autowired
	private PagamentoService pagamentoService;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private  MovimentacaoEstoqueService movimentacaoEstoqueService;
	@Autowired
	private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
	@Autowired
	private VendaItemRepository vendaItemRepository;

		public List<VendaResponseDTO> getAllVendas () {
			return vendaRepository.findAll().stream()
					.map(this::toResponseDTO)
					.collect(Collectors.toList());
		}

		public VendaResponseDTO getById (UUID id){
			return vendaRepository.findById(id)
					.map(this::toResponseDTO)
					.orElse(null);
		}
	@Transactional
	public VendaResponseDTO createVenda(VendaRequestDTO dto, Usuario usuario) {
		Cliente cliente = clienteRepository.findById(dto.getIdCliente())
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + dto.getIdCliente()));

		Venda venda = Venda.builder()
				.data(dto.getData())
				.usuario(usuario)
				.cliente(cliente)
				.statusVenda(StatusVenda.AGUARDANDO_PAGAMENTO)
				.build();
		venda = vendaRepository.save(venda);

		List<VendaItem> vendaItens = vendaItemService.criarItensVenda(venda, dto.getVendaItemList(), dto.getData(), usuario);

		venda.setVendaItens(vendaItens);

		PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO();
		pagamentoRequestDTO.setFormaPagamento(dto.getFormaPagamento());
		pagamentoRequestDTO.setStatusPagamento(StatusPagamento.PENDENTE);
		pagamentoRequestDTO.setValorTotal(venda.getValorTotal());

		PagamentoResponseDTO pagamentoResponseDTO = pagamentoService.criarPagamento(pagamentoRequestDTO);

		Pagamento pagamento = Pagamento.builder()
				.id(pagamentoResponseDTO.getId())
				.formaPagamento(pagamentoRequestDTO.getFormaPagamento())
				.statusPagamento(pagamentoResponseDTO.getStatusPagamento())
				.valorTotal(pagamentoRequestDTO.getValorTotal())
				.build();
		venda.setPagamento(pagamento);

		venda = vendaRepository.save(venda);

		if (pagamentoResponseDTO.getStatusPagamento() == StatusPagamento.APROVADO) {
			confirmarVenda(venda, usuario);
		} else if (pagamentoResponseDTO.getStatusPagamento() == StatusPagamento.RECUSADO) {
			cancelarVenda(venda);
		}

		return toResponseDTO(venda);
	}
	/*@Transactional
	public VendaResponseDTO updateVenda (UUID id, VendaRequestDTO dto){
		Venda venda = vendaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Venda não encontrada: " + id));

		return toResponseDTO(venda);
	}*/
	@Transactional
	public VendaResponseDTO updateVenda(UUID id, VendaRequestDTO dto, Usuario usuario) {
		Venda venda = vendaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Venda não encontrada: " + id));

		Cliente cliente = clienteRepository.findById(dto.getIdCliente())
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + dto.getIdCliente()));

		if (dto.getFormaPagamento() == null) {
			throw new IllegalArgumentException("Forma de pagamento é obrigatória.");
		}

		venda.setData(dto.getData());
		venda.setCliente(cliente);
		venda.getPagamento().setFormaPagamento(dto.getFormaPagamento());

		// 1. Desfaz movimentações antigas
		movimentacaoEstoqueService.desfazerMovimentacaoPorVenda(venda.getId());

		// 2. Remove itens antigos
		venda.getVendaItens().clear();
		vendaItemRepository.deleteByVendaId(venda.getId());

		// 3. Cria e adiciona novos itens (inclui movimentação de estoque)
		List<VendaItem> novosItens = vendaItemService.criarItensVenda(venda, dto.getVendaItemList(), dto.getData(), usuario);
		venda.getVendaItens().addAll(novosItens);

		venda.getPagamento().setValorTotal(venda.getValorTotal());

		venda = vendaRepository.save(venda);
		return toResponseDTO(venda);
	}




	@Transactional
		public void deleteVenda (UUID id){
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

		private VendaResponseDTO toResponseDTO (Venda venda){
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
					.statusVenda(venda.getStatusVenda())
					.build();
		}

	/*private void confirmarVenda(Venda venda, Usuario usuario) {
		for (VendaItem item : venda.getVendaItens()) {
			item.setStatusVendaItem(StatusVendaItem.CONFIRMADO);

			MovimentacaoEstoqueRequestDTO movimentacao = new MovimentacaoEstoqueRequestDTO(
					item.getProduto().getId(),
					venda.getData(),
					item.getQuantidade(),
					"VENDA",
					"SAIDA",
					null,
					venda.getId()
			);

			movimentacaoEstoqueService.criar(movimentacao, usuario);
		}

		vendaItemRepository.saveAll(venda.getVendaItens());

		venda.setStatusVenda(StatusVenda.CONFIRMADO);

		vendaRepository.save(venda);
	}*/

	private void confirmarVenda(Venda venda, Usuario usuario) {
		for (VendaItem item : venda.getVendaItens()) {
			item.setStatusVendaItem(StatusVendaItem.CONFIRMADO);
		}

		vendaItemRepository.saveAll(venda.getVendaItens());

		venda.setStatusVenda(StatusVenda.CONFIRMADO);
		vendaRepository.save(venda);
	}



	private void cancelarVenda(Venda venda) {
		for (VendaItem item : venda.getVendaItens()) {
			item.setStatusVendaItem(StatusVendaItem.CANCELADO);
		}

		vendaItemRepository.saveAll(venda.getVendaItens());

		venda.setStatusVenda(StatusVenda.CANCELADO);
		vendaRepository.save(venda);
	}
	}
