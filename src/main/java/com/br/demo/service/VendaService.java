package com.br.demo.service;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.dto.request.VendaRequestDTO;
import com.br.demo.dto.response.VendaResponseDTO;
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

	public VendaService(
			VendaRepository vendaRepository,
			VendaItemRepository vendaItemRepository,
			PagamentoRepository pagamentoRepository ,
			ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository,
			HistoricoValorRepository historicoValorRepository
	) {
		this.vendaRepository = vendaRepository;
		this.pagamentoRepository = pagamentoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
		this.vendaItemRepository = vendaItemRepository;
		this.historicoValorRepository = historicoValorRepository;
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

	@Transactional
	public VendaResponseDTO createVenda(VendaRequestDTO dto, Usuario usuario) {
		Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElse(null);
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

		List<VendaItem> vendaItens = new ArrayList<>();

		for (VendaItemDTO vendaItemDTO : dto.getVendaItemList()) {
			Produto produto = produtoRepository.findById(vendaItemDTO.getProdutoId())
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + vendaItemDTO.getProdutoId()));

			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - vendaItemDTO.getQuantidadeVenda());
			produtoRepository.save(produto);

			HistoricoValor precoVigente = historicoValorRepository.findPrecoVigenteByProdutoAndData(
					produto.getId(), dto.getData()
			).orElseThrow(() -> new IllegalArgumentException("Preço não encontrado para produto " + produto.getId()));

			VendaItem vendaItem = VendaItem.builder()
					.id(new VendaItemId(null, produto.getId())) // ID será setado pelo Hibernate após persistência
					.venda(venda)
					.produto(produto)
					.quantidade(vendaItemDTO.getQuantidadeVenda())
					.preçoUnitario(precoVigente.getPreçoUnitario())
					.build();

			vendaItens.add(vendaItem);
		}

		venda.setVendaItens(vendaItens);

		Venda savedVenda = vendaRepository.save(venda);

		return toResponseDTO(savedVenda);
	}

	@Transactional
	public VendaResponseDTO updateVenda(UUID id, VendaRequestDTO dto) {
		Venda venda = vendaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Venda não encontrada: " + id));

		Cliente cliente = clienteRepository.findById(dto.getIdCliente())
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + dto.getIdCliente()));

		Pagamento pagamento = pagamentoRepository.findById(dto.getIdPagamento())
				.orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: " + dto.getIdPagamento()));

		boolean dataAlterada = !venda.getData().equals(dto.getData());
		venda.setData(dto.getData());
		venda.setCliente(cliente);
		venda.setPagamento(pagamento);

		Set<UUID> idsProdutosNoDTO = dto.getVendaItemList().stream()
				.map(VendaItemDTO::getProdutoId)
				.collect(Collectors.toSet());

		List<VendaItem> vendaItensExistentes = venda.getVendaItens();

		for (Iterator<VendaItem> iterator = vendaItensExistentes.iterator(); iterator.hasNext(); ) {
			VendaItem vendaItem = iterator.next();
			if (!idsProdutosNoDTO.contains(vendaItem.getProduto().getId())) {
				Produto produto = vendaItem.getProduto();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + vendaItem.getQuantidade());
				produtoRepository.save(produto);

				vendaItemRepository.delete(vendaItem);
				iterator.remove();
			}
		}

		for (VendaItemDTO vendaItemDTO : dto.getVendaItemList()) {
			Produto produto = produtoRepository.findById(vendaItemDTO.getProdutoId())
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + vendaItemDTO.getProdutoId()));

			Optional<VendaItem> vendaItemExistenteOpt = vendaItensExistentes.stream()
					.filter(vendaItem -> vendaItem.getProduto().getId().equals(vendaItemDTO.getProdutoId()))
					.findFirst();

			VendaItem vendaItem;

			if (vendaItemExistenteOpt.isPresent()) {
				vendaItem = vendaItemExistenteOpt.get();

				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + vendaItem.getQuantidade());
			} else {
				vendaItem = new VendaItem();
				vendaItem.setId(new VendaItemId(venda.getId(), produto.getId()));
				vendaItem.setVenda(venda);
				vendaItem.setProduto(produto);
				vendaItensExistentes.add(vendaItem);
			}

			vendaItem.setQuantidade(vendaItemDTO.getQuantidadeVenda());
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - vendaItemDTO.getQuantidadeVenda());

			produtoRepository.save(produto);

			if (dataAlterada || !vendaItemExistenteOpt.isPresent()) {
				HistoricoValor precoVigente = historicoValorRepository.findPrecoVigenteByProdutoAndData(produto.getId(), venda.getData())
						.orElseThrow(() -> new IllegalArgumentException("Preço não encontrado para o produto na data informada"));

				vendaItem.setPreçoUnitario(precoVigente.getPreçoUnitario());
			}

			vendaItemRepository.save(vendaItem);
		}

		venda = vendaRepository.save(venda);

		return toResponseDTO(venda);
	}

	public void deleteVenda(UUID id) {
		vendaRepository.deleteById(id);
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
				.build();
	}
}
