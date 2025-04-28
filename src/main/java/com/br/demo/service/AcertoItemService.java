package com.br.demo.service;

import com.br.demo.dto.request.AcertoItemRequestDTO;
import com.br.demo.dto.response.AcertoItemResponseDTO;
import com.br.demo.enums.TipoAcerto;
import com.br.demo.model.*;
import com.br.demo.repository.AcertoItemRepository;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AcertoItemService {
	
	private final AcertoItemRepository itemRepository;
	private final ProdutoRepository produtoRepository;
	
	
	public AcertoItemService(AcertoItemRepository itemRepository, ProdutoRepository produtoRepository) {
		this.itemRepository = itemRepository;
		this.produtoRepository = produtoRepository;
		
	}
	
	public List<AcertoItemResponseDTO> listar() {
		return itemRepository.findAll().stream()
					   .map(this::toDTO)
					   .collect(Collectors.toList());
	}
	
	public AcertoItemResponseDTO criar(AcertoItemRequestDTO dto, Usuario usuario) {
		Produto produto = produtoRepository.findById(dto.getIdProduto())
								  .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
		
		AcertoItem item = AcertoItem.builder()
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
	
	private AcertoItemResponseDTO toDTO(AcertoItem item) {
		return new AcertoItemResponseDTO(
				item.getProduto().getId(),
				item.getData(),
				item.getQuantidade(),
				item.getObservacao(),
				item.getTipoAcerto(),
				item.getUsuario().getId()
		);
	}
	
	public AcertoItemResponseDTO buscarPorId(UUID id) {
		AcertoItem item = itemRepository.findById(id)
								  .orElseThrow(() -> new IllegalArgumentException("AcertoItem não encontrado com ID: " + id));
		
		return toDTO(item);
	}
}