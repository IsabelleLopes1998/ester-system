package com.br.demo.service;

import com.br.demo.dto.request.CompraItemRequestDTO;
import com.br.demo.dto.request.CompraRequestDTO;
import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.CompraItemResponseDTO;
import com.br.demo.dto.response.CompraResponseDTO;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompraService {
    @Autowired
    private CompraItemService compraItemService;
    private final CompraRepository compraRepository;
    private final CompraItemRepository compraItemRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    
    public CompraService(
            CompraItemService compraItemService,
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
        this.compraItemService = compraItemService;
    }

    @Transactional
    public CompraResponseDTO criarCompra(CompraRequestDTO dto, Usuario usuario) {
        System.out.println("Valor total recebido: " + dto.getValorTotalDaCompra());

        Compra compra = Compra.builder()
                .data(dto.getData())
                .fornecedor(dto.getFornecedor())
                .usuario(usuario)
                .valorTotalDaCompra(dto.getValorTotalDaCompra())
                .build();
        compra = compraRepository.save(compra);

        final Compra compraFinal = compra;

        List<CompraItem> compraItens = compraItemService.criarItensCompra(compraFinal, dto.getItens(), dto.getData());

        compra.setCompraItens(compraItens);


        compra = compraRepository.save(compra);

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
                .valorTotalDaCompra(compra.getValorTotalDaCompra())
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
                            .valorTotalDaCompra(compra.getValorTotalDaCompra())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
