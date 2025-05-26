package com.br.demo.service;

import com.br.demo.dto.request.CategoriaRequestDTO;
import com.br.demo.dto.response.CategoriaResponseDTO;
import com.br.demo.model.Categoria;
import com.br.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> new CategoriaResponseDTO(
                        categoria.getId(),
                        categoria.getNome(),
                        categoria.getDescricao()))
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(UUID id) {
        return categoriaRepository.findById(id)
                .map(categoria -> new CategoriaResponseDTO(
                        categoria.getId(),
                        categoria.getNome(),
                        categoria.getDescricao()))
                .orElse(null);
    }

    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = Categoria.builder()
                .nome(categoriaRequestDTO.getNome())
                .descricao(categoriaRequestDTO.getDescricao())
                .build();

        categoria = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao());
    }

    public CategoriaResponseDTO atualizarCategoria(UUID id, CategoriaRequestDTO categoriaRequestDTO) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            categoria.setNome(categoriaRequestDTO.getNome());
            categoria.setDescricao(categoriaRequestDTO.getDescricao());
            categoriaRepository.save(categoria); // 🔥 Atualiza no banco!

            return new CategoriaResponseDTO(
                    categoria.getId(),
                    categoria.getNome(),
                    categoria.getDescricao());
        }
        return null;
    }

    public void excluirCategoria(UUID id) {
        categoriaRepository.deleteById(id);
    }
}
