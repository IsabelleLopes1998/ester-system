package com.br.demo.service;

import com.br.demo.dto.CompraItemDTO;
import com.br.demo.dto.VendaItemDTO;
import com.br.demo.dto.request.CompraItemRequestDTO;
import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.VendaItemResponseDTO;
import com.br.demo.enums.StatusVendaItem;
import com.br.demo.model.*;
import com.br.demo.repository.CompraItemRepository;
import com.br.demo.repository.HistoricoValorRepository;
import com.br.demo.repository.ProdutoRepository;
import com.br.demo.repository.VendaItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraItemService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CompraItemRepository compraItemRepository;

    @Autowired
    private MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Transactional
    public List<CompraItem> criarItensCompra(Compra compra, List<CompraItemDTO> compraItemDTOs, LocalDateTime dataCompra) {
        List<CompraItem> compraItens = new ArrayList<>();

        for (CompraItemDTO dto : compraItemDTOs) {
            Produto produto = produtoRepository.findById(dto.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + dto.getProdutoId()));

            CompraItem item = CompraItem.builder()
                    .id(new CompraItemId(compra.getId(), produto.getId()))
                    .compra(compra)
                    .produto(produto)
                    .quantidade(dto.getQuantidade())
                    .preçoUnitario(produto.getPrecoVigente()) // ou dto.getPrecoUnitario(), se vier do front
                    .build();

            MovimentacaoEstoqueRequestDTO movimentacaoEstoqueRequestDTO = new MovimentacaoEstoqueRequestDTO(produto.getId(),dataCompra,item.getQuantidade(),null,"ENTRADA",null,null);
            movimentacaoEstoqueService.criar(movimentacaoEstoqueRequestDTO,compra.getUsuario());

            compraItens.add(item);
        }

        return compraItemRepository.saveAll(compraItens);
    }
}
