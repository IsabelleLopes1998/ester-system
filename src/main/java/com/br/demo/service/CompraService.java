package com.br.demo.service;

import com.br.demo.dto.request.CompraItemRequestDTO;
import com.br.demo.dto.request.CompraRequestDTO;
import com.br.demo.dto.response.CompraItemResponseDTO;
import com.br.demo.dto.response.CompraResponseDTO;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final CompraItemRepository compraItemRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagamentoRepository pagamentoRepository;

    public CompraService(
            CompraRepository compraRepository,
            CompraItemRepository compraItemRepository,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository,
            PagamentoRepository pagamentoRepository
    ) {
        this.compraRepository = compraRepository;
        this.compraItemRepository = compraItemRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public CompraResponseDTO criarCompra(CompraRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow();
        Pagamento pagamento = pagamentoRepository.findById(dto.getPagamentoId()).orElseThrow();

        Compra compra = Compra.builder()
                .data(dto.getData())
                .fornecedor(dto.getFornecedor())
                .usuario(usuario)
                .pagamento(pagamento)
                .quantidadeParcelas(dto.getQuantidadeParcelas())
                .build();
        compra = compraRepository.save(compra);

        final Compra compraFinal = compra;

        List<CompraItem> compraItens = dto.getItens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId()).orElseThrow();


            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + itemDTO.getQuantidadeVenda());
            produtoRepository.save(produto);


            CompraItemId compraItemId = new CompraItemId(produto.getId(), compraFinal.getId());
            return CompraItem.builder()
                    .id(compraItemId)
                    .produto(produto)
                    .compra(compraFinal)
                    .quantidadeVenda(itemDTO.getQuantidadeVenda())
                    .valorUnitario(itemDTO.getValorUnitario())
                    .build();
        }).collect(Collectors.toList());

        compraItemRepository.saveAll(compraItens);


        List<CompraItemResponseDTO> itensResponse = compraItens.stream()
                .map(item -> new CompraItemResponseDTO(
                        item.getProduto().getNome(),
                        item.getValorUnitario(),
                        item.getQuantidadeVenda()
                ))
                .collect(Collectors.toList());

        return CompraResponseDTO.builder()
                .id(compra.getId())
                .data(compra.getData())
                .fornecedor(compra.getFornecedor())
                .nomeUsuario(compra.getUsuario().getNome())
                .formaPagamento(compra.getPagamento().getFormaPagamento().toString())
                .quantidadeParcelas(compra.getQuantidadeParcelas())
                .itens(itensResponse)
                .build();
    }

    public List<CompraResponseDTO> listarCompras() {
        return compraRepository.findAll().stream()
                .map(compra -> CompraResponseDTO.builder()
                        .id(compra.getId())
                        .data(compra.getData())
                        .fornecedor(compra.getFornecedor())
                        .nomeUsuario(compra.getUsuario().getNome())
                        .formaPagamento(compra.getPagamento().getFormaPagamento().toString())
                        .quantidadeParcelas(compra.getQuantidadeParcelas())
                        .itens(null) // Opcional: podemos popular se quiser
                        .build())
                .collect(Collectors.toList());
    }
}
