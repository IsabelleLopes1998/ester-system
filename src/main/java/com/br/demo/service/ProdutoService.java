package com.br.demo.service;

import com.br.demo.dto.request.ProdutoRequestDTO;
import com.br.demo.dto.response.ProdutoResponseDTO;
import com.br.demo.model.Categoria;
import com.br.demo.model.Produto;
import com.br.demo.model.Subcategoria;
import com.br.demo.model.Usuario;
import com.br.demo.repository.CategoriaRepository;
import com.br.demo.repository.ProdutoRepository;
import com.br.demo.repository.SubcategoriaRepository;
import com.br.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            CategoriaRepository categoriaRepository,
            SubcategoriaRepository subcategoriaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
                .map(produto -> new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreçoVigente(),
                        produto.getQuantidadeEstoque(),
                        produto.getCategoria().getNome(),
                        produto.getSubcategoria() != null ? produto.getSubcategoria().getNome() : null
                ))
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(UUID id) {
        return produtoRepository.findById(id)
                .map(produto -> new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreçoVigente(),
                        produto.getQuantidadeEstoque(),
                        produto.getCategoria().getNome(),
                        produto.getSubcategoria() != null ? produto.getSubcategoria().getNome() : null
                ))
                .orElse(null);
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).orElse(null);
        //Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        Subcategoria subcategoria = null;

        if (dto.getIdSubcategoria() != null) {
            subcategoria = subcategoriaRepository.findById(dto.getIdSubcategoria()).orElse(null);
        }

        if (categoria == null /*|| usuario == null*/) {
            return null;
        }
        // apaguei o .usuario(usuario)
        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .preçoVigente(dto.getValor())
                .quantidadeEstoque(dto.getQuantidadeEstoque())
                .categoria(categoria)
                .subcategoria(subcategoria)
                .build();

        produto = produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreçoVigente(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria().getNome(),
                produto.getSubcategoria() != null ? produto.getSubcategoria().getNome() : null
        );
    }

    public ProdutoResponseDTO atualizarProduto(UUID id, ProdutoRequestDTO dto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(dto.getNome());
            produto.setPreçoVigente(dto.getValor());
            produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());

            Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).orElse(null);
            Subcategoria subcategoria = null;
            if (dto.getIdSubcategoria() != null) {
                subcategoria = subcategoriaRepository.findById(dto.getIdSubcategoria()).orElse(null);
            }

            if (categoria != null) {
                produto.setCategoria(categoria);
            }
            produto.setSubcategoria(subcategoria);

            produtoRepository.save(produto);

            return new ProdutoResponseDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreçoVigente(),
                    produto.getQuantidadeEstoque(),
                    produto.getCategoria().getNome(),
                    produto.getSubcategoria() != null ? produto.getSubcategoria().getNome() : null
            );
        }
        return null;
    }

    public void excluirProduto(UUID id) {
        produtoRepository.deleteById(id);
    }
}
