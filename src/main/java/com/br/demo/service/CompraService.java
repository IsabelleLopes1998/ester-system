package com.br.demo.service;

import com.br.demo.dto.request.CompraItemRequestDTO;
import com.br.demo.dto.request.CompraRequestDTO;
import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.CompraItemResponseDTO;
import com.br.demo.dto.response.CompraResponseDTO;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final CompraItemRepository compraItemRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    
    public CompraService(
            CompraRepository compraRepository,
            CompraItemRepository compraItemRepository,
            ProdutoRepository produtoRepository,
            PagamentoRepository pagamentoRepository,
            MovimentacaoEstoqueService movimentacaoEstoqueService
    ) {
        this.compraRepository = compraRepository;
        this.compraItemRepository = compraItemRepository;
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @Transactional
    public CompraResponseDTO criarCompra(CompraRequestDTO dto, Usuario usuario) {

        Compra compra = Compra.builder()
                .data(dto.getData())
                .fornecedor(dto.getFornecedor())
                .usuario(usuario)
                .build();
        compra = compraRepository.save(compra);

        final Compra compraFinal = compra;

        List<CompraItem> compraItens = dto.getItens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId()).orElseThrow();


            MovimentacaoEstoqueRequestDTO novaMovimentacao = new MovimentacaoEstoqueRequestDTO(itemDTO.getProdutoId() ,dto.getData(), itemDTO.getQuantidadeVenda(), "COMPRA", "ENTRADA", compraFinal.getId(), null);
            movimentacaoEstoqueService.criar(novaMovimentacao,usuario);
            produtoRepository.save(produto);


            CompraItemId compraItemId = new CompraItemId(produto.getId(), compraFinal.getId());
            return CompraItem.builder()
                    .id(compraItemId)
                    .produto(produto)
                    .compra(compraFinal)
                    .quantidade(itemDTO.getQuantidadeVenda())
                    .preçoUnitario(itemDTO.getValorUnitario())
                    .build();
        }).collect(Collectors.toList());

        compraItemRepository.saveAll(compraItens);


        List<CompraItemResponseDTO> itensResponse = compraItens.stream()
                .map(item -> new CompraItemResponseDTO(
                        item.getProduto().getNome(),
                        item.getPreçoUnitario(),
                        item.getQuantidade()
                ))
                .collect(Collectors.toList());

        return CompraResponseDTO.builder()
                .id(compra.getId())
                .data(compra.getData())
                .fornecedor(compra.getFornecedor())
                .nomeUsuario(compra.getUsuario().getNome())
                .itens(itensResponse)
                .build();
    }

    public List<CompraResponseDTO> listarCompras() {
        return compraRepository.findAll().stream()
                .map(compra -> {
                    List<CompraItemResponseDTO> itensResponse = compra.getCompraItens().stream()
                            .map(item -> new CompraItemResponseDTO(
                                    item.getProduto().getNome(),
                                    item.getPreçoUnitario(),
                                    item.getQuantidade()
                            ))
                            .collect(Collectors.toList());

                    return CompraResponseDTO.builder()
                            .id(compra.getId())
                            .data(compra.getData())
                            .fornecedor(compra.getFornecedor())
                            .nomeUsuario(compra.getUsuario().getNome())
                            .itens(itensResponse)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
