package com.br.demo.service;

import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.MovimentacaoEstoqueResponseDTO;
import com.br.demo.enums.TipoAcerto;
import com.br.demo.model.*;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovimentacaoEstoque {
	
	private final com.br.demo.repository.MovimentacaoEstoque itemRepository;
	private final ProdutoRepository produtoRepository;
	
	
	public MovimentacaoEstoque (com.br.demo.repository.MovimentacaoEstoque itemRepository, ProdutoRepository produtoRepository) {
		this.itemRepository = itemRepository;
		this.produtoRepository = produtoRepository;
		
	}
	
	public List<MovimentacaoEstoqueResponseDTO> listar() {
		return itemRepository.findAll().stream()
					   .map(this::toDTO)
					   .collect(Collectors.toList());
	}
	
	public MovimentacaoEstoqueResponseDTO criar(MovimentacaoEstoqueRequestDTO dto, Usuario usuario) {
		Produto produto = produtoRepository.findById(dto.getIdProduto())
								  .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
		
		com.br.demo.model.MovimentacaoEstoque item = com.br.demo.model.MovimentacaoEstoque.builder()
								  .produto(produto)
								  .data(dto.getData())
								  .quantidade(dto.getQuantidade())
				                  .tipoAcerto(TipoAcerto.valueOf(dto.getTipoAcerto()))
								  .observacao(dto.getObservacao())
				                  .usuario(usuario)
								  .build();
		
		item = itemRepository.save(item);
		return toDTO(item);
	}
	
	public void excluir(UUID idAcerto) {
		itemRepository.deleteById(idAcerto);
	}
	
	private MovimentacaoEstoqueResponseDTO toDTO(com.br.demo.model.MovimentacaoEstoque item) {
		return new MovimentacaoEstoqueResponseDTO(
				item.getProduto().getId(),
				item.getData(),
				item.getQuantidade(),
				item.getObservacao(),
				item.getTipoAcerto(),
				item.getUsuario().getId()
		);
	}
	
	public MovimentacaoEstoqueResponseDTO buscarPorId(UUID id) {
		com.br.demo.model.MovimentacaoEstoque item = itemRepository.findById(id)
								  .orElseThrow(() -> new IllegalArgumentException("AcertoItem não encontrado com ID: " + id));
		
		return toDTO(item);
	}
}