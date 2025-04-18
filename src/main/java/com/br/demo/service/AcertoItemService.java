package com.br.demo.service;

import com.br.demo.dto.request.AcertoItemRequestDTO;
import com.br.demo.dto.response.AcertoItemResponseDTO;
import com.br.demo.model.*;
import com.br.demo.repository.AcertoItemRepository;
import com.br.demo.repository.AcertoEstoqueRepository;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AcertoItemService {
	
	private final AcertoItemRepository itemRepository;
	private final ProdutoRepository produtoRepository;
	private final AcertoEstoqueRepository acertoRepository;
	
	public AcertoItemService(AcertoItemRepository itemRepository, ProdutoRepository produtoRepository, AcertoEstoqueRepository acertoRepository) {
		this.itemRepository = itemRepository;
		this.produtoRepository = produtoRepository;
		this.acertoRepository = acertoRepository;
	}
	
	public List<AcertoItemResponseDTO> listar() {
		return itemRepository.findAll().stream()
					   .map(this::toDTO)
					   .collect(Collectors.toList());
	}
	
	public AcertoItemResponseDTO criar(AcertoItemRequestDTO dto) {
		Produto produto = produtoRepository.findById(dto.getIdProduto())
								  .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
		
		AcertoEstoque acerto = acertoRepository.findById(dto.getIdAcerto())
									   .orElseThrow(() -> new IllegalArgumentException("Acerto não encontrado"));
		
		AcertoItem item = AcertoItem.builder()
								  .id(new AcertoItemId(dto.getIdProduto(), dto.getIdAcerto()))
								  .produto(produto)
								  .acerto(acerto)
								  .data(dto.getData())
								  .quantidade(dto.getQuantidade())
								  .valor(dto.getValor())
								  .observacao(dto.getObservacao())
								  .build();
		
		item = itemRepository.save(item);
		return toDTO(item);
	}
	
	public void excluir(UUID idProduto, UUID idAcerto) {
		AcertoItemId id = new AcertoItemId(idProduto, idAcerto);
		itemRepository.deleteById(id);
	}
	
	private AcertoItemResponseDTO toDTO(AcertoItem item) {
		return new AcertoItemResponseDTO(
				item.getProduto().getId(),
				item.getAcerto().getId(),
				item.getData(),
				item.getQuantidade(),
				item.getValor(),
				item.getObservacao()
		);
	}
}