package com.br.demo.service;

import com.br.demo.dto.request.ProdutoRequestDTO;
import com.br.demo.dto.response.ProdutoResponseDTO;
import com.br.demo.model.Categoria;
import com.br.demo.model.Produto;
import com.br.demo.repository.CategoriaRepository;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
                .map(produto -> new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getCategoria().getNome()))
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(UUID id) {
        return produtoRepository.findById(id)
                .map(produto -> new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getCategoria().getNome()))
                .orElse(null);
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElse(null);

        if (categoria == null) {
            return null;
        }

        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .preco(dto.getPreco())
                .numeroSerie(dto.getNumeroSerie())
                .categoria(categoria)
                .build();

        produto = produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria().getNome());
    }

    public ProdutoResponseDTO atualizarProduto(UUID id, ProdutoRequestDTO dto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(dto.getNome());
            produto.setPreco(dto.getPreco());
            produto.setNumeroSerie(dto.getNumeroSerie());

            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElse(null);

            if (categoria != null) {
                produto.setCategoria(categoria);
            }

            produtoRepository.save(produto);

            return new ProdutoResponseDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getCategoria().getNome());
        }
        return null;
    }

    public void excluirProduto(UUID id) {
        produtoRepository.deleteById(id);
    }
}

