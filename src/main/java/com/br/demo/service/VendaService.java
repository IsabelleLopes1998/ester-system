package com.br.demo.service;

import com.br.demo.dto.VendaItemDTO;
import com.br.demo.dto.request.VendaRequestDTO;
import com.br.demo.dto.response.VendaResponseDTO;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VendaService {
	
	private final VendaRepository vendaRepository;
	private final PagamentoRepository pagamentoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;
	public VendaService(
			VendaRepository vendaRepository,
			PagamentoRepository pagamentoRepository ,
			ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository
	) {
		this.vendaRepository = vendaRepository;
		this.pagamentoRepository = pagamentoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
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

			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - vendaItem.getQuantidadeVenda());
			produtoRepository.save(produto);
		}
		
		Venda venda = Venda.builder()
							  .data(dto.getData())
							  .usuario(usuario)
							  .cliente(cliente)
							  .pagamento(pagamento)
							  .build();
		
		venda = vendaRepository.save(venda);
		
		return toResponseDTO(venda);
	}
	
	public VendaResponseDTO updateVenda(UUID id, VendaRequestDTO dto) {
		Optional<Venda> optionalVenda = vendaRepository.findById(id);
		
		if (optionalVenda.isPresent()) {
			Venda venda = optionalVenda.get();
			
			Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElse(null);
			Pagamento pagamento = pagamentoRepository.findById(dto.getIdPagamento()).orElse(null);
			
			if (cliente != null) venda.setCliente(cliente);
			if (pagamento != null) venda.setPagamento(pagamento);
			
			venda.setData(dto.getData());
			
			venda = vendaRepository.save(venda);
			
			return toResponseDTO(venda);
		}
		
		return null;
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
