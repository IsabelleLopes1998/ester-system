package com.br.demo.service;

import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.MovimentacaoEstoqueResponseDTO;
import com.br.demo.enums.TipoMovimentacao;
import com.br.demo.model.*;
import com.br.demo.repository.CompraRepository;
import com.br.demo.repository.MovimentacaoEstoqueRepository;
import com.br.demo.repository.ProdutoRepository;
import com.br.demo.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovimentacaoEstoqueService {
	
	private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
	private final ProdutoRepository produtoRepository;
	private final VendaRepository vendaRepository;
	private final CompraRepository compraRepository;

	public MovimentacaoEstoqueService (MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, ProdutoRepository produtoRepository, VendaRepository vendaRepository, CompraRepository compraRepository) {
		this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
		this.produtoRepository = produtoRepository;
		this.vendaRepository = vendaRepository;
		this.compraRepository = compraRepository;
	}
	
	public List<MovimentacaoEstoqueResponseDTO> listar() {
		return movimentacaoEstoqueRepository.findAll().stream()
					   .map(this::toDTO)
					   .collect(Collectors.toList());
	}
	
	public MovimentacaoEstoqueResponseDTO criar(MovimentacaoEstoqueRequestDTO dto, Usuario usuario) {
		Produto produto = produtoRepository.findById(dto.getIdProduto())
								  .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

		TipoMovimentacao tipoMovimentacao = TipoMovimentacao.valueOf(dto.getTipoAcerto());

		tipoMovimentacao.aplicar(produto, dto.getQuantidade());
		produtoRepository.save(produto);

		if (dto.getIdVenda() != null && dto.getIdCompra() != null) {
			throw new RuntimeException("uma movimentacao não pode estar atribuida a uma compra e a uma venda ao mesmo tempo!");
		}

		Venda venda = null;
		Compra compra = null;
		if(dto.getIdVenda() != null ) {
			venda = vendaRepository.findById(dto.getIdVenda())
					.orElseThrow(() -> new RuntimeException("Venda não encontrada"));
		}
		if(dto.getIdCompra() != null ) {
			compra = compraRepository.findById(dto.getIdCompra())
					.orElseThrow(() -> new RuntimeException("Compra não encontrada"));
		}


		MovimentacaoEstoque item = MovimentacaoEstoque.builder()
								  .produto(produto)
								  .data(dto.getData())
								  .quantidade(dto.getQuantidade())
				                  .tipoMovimentacao(TipoMovimentacao.valueOf(dto.getTipoAcerto()))
								  .observacao(dto.getObservacao())
				                  .usuario(usuario)
								  .compra(compra)
				                  .venda(venda)
								  .build();
		
		item = movimentacaoEstoqueRepository.save(item);

		return toDTO(item);
	}
	
	public void excluir(UUID idAcerto) {
		movimentacaoEstoqueRepository.deleteById(idAcerto);
	}
	
	private MovimentacaoEstoqueResponseDTO toDTO(MovimentacaoEstoque item) {
		return new MovimentacaoEstoqueResponseDTO(
				item.getId(),
				item.getProduto().getId(),
				item.getData(),
				item.getQuantidade(),
				item.getObservacao(),
				item.getTipoMovimentacao(),
				item.getUsuario().getId(),
				item.getCompra() != null ? item.getCompra().getId() : null,
				item.getVenda() != null ? item.getVenda().getId() : null
		);
	}
	
	public MovimentacaoEstoqueResponseDTO buscarPorId(UUID id) {
		com.br.demo.model.MovimentacaoEstoque item = movimentacaoEstoqueRepository.findById(id)
								  .orElseThrow(() -> new IllegalArgumentException("AcertoItem não encontrado com ID: " + id));
		
		return toDTO(item);
	}
}