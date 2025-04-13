package com.br.demo.service;

import com.br.demo.dto.response.CompraItemResponseDTO;
import com.br.demo.repository.CompraItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraItemService {

    private final CompraItemRepository compraItemRepository;

    public CompraItemService(CompraItemRepository compraItemRepository) {
        this.compraItemRepository = compraItemRepository;
    }

    public List<CompraItemResponseDTO> listarCompraItens() {
        return compraItemRepository.findAll().stream()
                .map(item -> CompraItemResponseDTO.builder()
                        .nomeProduto(item.getProduto().getNome())
                        .valorUnitario(item.getValorUnitario())
                        .quantidadeVenda(item.getQuantidadeVenda())
                        .build())
                .collect(Collectors.toList());
    }
}
