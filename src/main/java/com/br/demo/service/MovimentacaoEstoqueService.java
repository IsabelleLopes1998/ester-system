package com.br.demo.service;

import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.MovimentacaoEstoqueResponseDTO;
import com.br.demo.enums.TipoMovimentacao;
import com.br.demo.model.*;
import com.br.demo.repository.MovimentacaoEstoqueRepository;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovimentacaoEstoqueService {
	
	private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
	private final ProdutoRepository produtoRepository;
	
	
	public MovimentacaoEstoqueService (MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, ProdutoRepository produtoRepository) {
		this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
		this.produtoRepository = produtoRepository;
		
	}
	
	public List<MovimentacaoEstoqueResponseDTO> listar() {
		return movimentacaoEstoqueRepository.findAll().stream()
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
				                  .tipoMovimentacao(TipoMovimentacao.valueOf(dto.getTipoAcerto()))
								  .observacao(dto.getObservacao())
				                  .usuario(usuario)
								  .build();
		
		item = movimentacaoEstoqueRepository.save(item);
		return toDTO(item);
	}
	
	public void excluir(UUID idAcerto) {
		movimentacaoEstoqueRepository.deleteById(idAcerto);
	}
	
	private MovimentacaoEstoqueResponseDTO toDTO(com.br.demo.model.MovimentacaoEstoque item) {
		return new MovimentacaoEstoqueResponseDTO(
				item.getId(),
				item.getProduto().getId(),
				item.getData(),
				item.getQuantidade(),
				item.getObservacao(),
				item.getTipoMovimentacao(),
				item.getUsuario().getId()
		);
	}
	
	public MovimentacaoEstoqueResponseDTO buscarPorId(UUID id) {
		com.br.demo.model.MovimentacaoEstoque item = movimentacaoEstoqueRepository.findById(id)
								  .orElseThrow(() -> new IllegalArgumentException("AcertoItem não encontrado com ID: " + id));
		
		return toDTO(item);
	}
}