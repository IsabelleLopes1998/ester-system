package com.br.demo.service;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.dto.request.VendaRequestDTO;
import com.br.demo.dto.response.VendaResponseDTO;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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
			return null;
		}

		for(VendaItemDTO vendaItem : dto.getVendaItemList()) {
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

		for (VendaItemDTO vendaItem : dto.getVendaItemList()) {
			Produto produto = produtoRepository.findById(vendaItem.getProdutoId())
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + vendaItem.getProdutoId()));

			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - vendaItem.getQuantidadeVenda());
			produtoRepository.save(produto);

			VendaItemId vendaItemId = new VendaItemId(venda.getId(), produto.getId());
			HistoricoValor precoVingente = historicoValorRepository.findPrecoVigenteByProdutoAndData(produto.getId(), venda.getData())
								.orElseThrow(() -> new IllegalArgumentException("Preco do Produto não encontrado: " + vendaItem.getProdutoId()));
			VendaItem vendaItemEntity = VendaItem.builder()
					.id(vendaItemId)
					.venda(venda)
					.produto(produto)
					.quantidadeVenda(vendaItem.getQuantidadeVenda())
					.valorUnitario(precoVingente.getValor())
					.build();

			vendaItemRepository.save(vendaItemEntity);
		}


		return toResponseDTO(venda);
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
		venda.setData(dto.getData()); // Atualiza a data da venda, caso tenha sido modificada

		venda.setCliente(cliente);
		venda.setPagamento(pagamento);

		Set<UUID> idsProdutosNoDTO = dto.getVendaItemList().stream()
				.map(VendaItemDTO::getProdutoId)
				.collect(Collectors.toSet());

		List<VendaItem> vendaItensExistentes = venda.getVendaItens();
		for (VendaItem vendaItem : vendaItensExistentes) {
			if (!idsProdutosNoDTO.contains(vendaItem.getProduto().getId())) {
				vendaItemRepository.delete(vendaItem); // Remover item que não foi enviado no DTO
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
				vendaItem.setQuantidadeVenda(vendaItemDTO.getQuantidadeVenda());
			} else {
				vendaItem = new VendaItem();
				vendaItem.setId(new VendaItemId(venda.getId(), produto.getId()));
				vendaItem.setVenda(venda);
				vendaItem.setProduto(produto);
				vendaItem.setQuantidadeVenda(vendaItemDTO.getQuantidadeVenda());
			}

			if (dataAlterada) {
				HistoricoValor precoVigente = historicoValorRepository.findPrecoVigenteByProdutoAndData(produto.getId(), venda.getData())
						.orElseThrow(() -> new IllegalArgumentException("Preço não encontrado para o produto na data informada"));

				vendaItem.setValorUnitario(precoVigente.getValor());
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
					   .build();
	}
}
