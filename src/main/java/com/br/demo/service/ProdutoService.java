package com.br.demo.service;

import com.br.demo.dto.request.HistoricoValorRequestDTO;
import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.request.ProdutoRequestDTO;
import com.br.demo.dto.response.ProdutoResponseDTO;
import com.br.demo.model.*;
import com.br.demo.repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final HistoricoValorRepository historicoValorRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    public ProdutoService(
            ProdutoRepository produtoRepository,
            CategoriaRepository categoriaRepository,
            HistoricoValorRepository historicoValorRepository,
            MovimentacaoEstoqueService movimentacaoEstoqueService
    ) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.movimentacaoEstoqueService =  movimentacaoEstoqueService;
        this.historicoValorRepository = historicoValorRepository;
    }
    
    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
                       .map(produto -> new ProdutoResponseDTO(
                               produto.getId(),
                               produto.getNome(),
                               produto.getPrecoVigente(),
                               produto.getQuantidadeEstoque(),
                               produto.getCategoria().getNome()
                       ))
                       .collect(Collectors.toList());
    }
    

    public ProdutoResponseDTO buscarPorId(UUID id) {
        return produtoRepository.findById(id)
                .map(produto -> new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPrecoVigente(),
                        produto.getQuantidadeEstoque(),
                        produto.getCategoria().getNome()
                ))
                .orElse(null);
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto, @AuthenticationPrincipal Usuario usuario) {
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).orElse(null);
        
        if (categoria == null || usuario == null) {
            return null;
        }
        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .precoVigente(dto.getValor())
                .quantidadeEstoque(0)
                .categoria(categoria)
                .usuario(usuario)
                .build();

        produto = produtoRepository.save(produto);

        HistoricoValor historico = new HistoricoValor();
        historico.setData(produto.getCreatedAt());
        historico.setPrecoUnitario(produto.getPrecoVigente());
        historico.setProduto(produto);

        historicoValorRepository.save(historico);
        System.out.println(produto.getQuantidadeEstoque());
        MovimentacaoEstoqueRequestDTO movimentacaoEstoqueRequestDTO = new MovimentacaoEstoqueRequestDTO(produto.getId(),produto.getCreatedAt(),dto.getQuantidadeEstoque(),null,"ENTRADA_MANUAL",null,null);
        movimentacaoEstoqueService.criar(movimentacaoEstoqueRequestDTO,usuario);
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPrecoVigente(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria().getNome()
        );
    }

    public ProdutoResponseDTO atualizarProduto(UUID id, ProdutoRequestDTO dto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(dto.getNome());
            produto.setPrecoVigente(dto.getValor());
            produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());

            Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).orElse(null);

            if (categoria != null) {
                produto.setCategoria(categoria);
            }

            produtoRepository.save(produto);

            return new ProdutoResponseDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPrecoVigente(),
                    produto.getQuantidadeEstoque(),
                    produto.getCategoria().getNome()
            );
        }
        return null;
    }

    public void excluirProduto(UUID id) {
        produtoRepository.deleteById(id);
    }
}
