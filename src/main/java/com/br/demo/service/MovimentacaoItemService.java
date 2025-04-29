package com.br.demo.service;

import com.br.demo.dto.request.MovimentacaoItemRequestDTO;
import com.br.demo.dto.response.MovimentacaoItemResponseDTO;
import com.br.demo.enums.TipoMovimentacao;
import com.br.demo.model.*;
import com.br.demo.repository.MovimentacaoItemRepository;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovimentacaoItemService {
	
	private final MovimentacaoItemRepository itemRepository;
	private final ProdutoRepository produtoRepository;
	
	
	public MovimentacaoItemService (MovimentacaoItemRepository itemRepository, ProdutoRepository produtoRepository) {
		this.itemRepository = itemRepository;
		this.produtoRepository = produtoRepository;
		
	}
	
	public List<MovimentacaoItemResponseDTO> listar() {
		return itemRepository.findAll().stream()
					   .map(this::toDTO)
					   .collect(Collectors.toList());
	}
	
	public MovimentacaoItemResponseDTO criar(MovimentacaoItemRequestDTO dto, Usuario usuario) {
		Produto produto = produtoRepository.findById(dto.getIdProduto())
								  .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
		
		MovimentacaoEstoque item = MovimentacaoEstoque.builder()
								  .produto(produto)
								  .data(dto.getData())
								  .quantidade(dto.getQuantidade())
				                  .tipoMovimentacao(TipoMovimentacao.valueOf(dto.getTipoAcerto()))
								  .observacao(dto.getObservacao())
				                  .usuario(usuario)
								  .build();
		
		item = itemRepository.save(item);
		return toDTO(item);
	}
	
	public void excluir(UUID idAcerto) {
		itemRepository.deleteById(idAcerto);
	}
	
	private MovimentacaoItemResponseDTO toDTO(MovimentacaoEstoque item) {
		return new MovimentacaoItemResponseDTO(
				item.getProduto().getId(),
				item.getData(),
				item.getQuantidade(),
				item.getObservacao(),
				item.getTipoMovimentacao(),
				item.getUsuario().getId()
		);
	}
	
	public MovimentacaoItemResponseDTO buscarPorId(UUID id) {
		MovimentacaoEstoque item = itemRepository.findById(id)
								  .orElseThrow(() -> new IllegalArgumentException("AcertoItem não encontrado com ID: " + id));
		
		return toDTO(item);
	}
}