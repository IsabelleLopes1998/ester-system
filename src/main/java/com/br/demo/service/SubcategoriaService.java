package com.br.demo.service;

import com.br.demo.dto.request.SubcategoriaRequestDTO;
import com.br.demo.dto.response.SubcategoriaResponseDTO;
import com.br.demo.model.Categoria;
import com.br.demo.model.Subcategoria;
import com.br.demo.repository.CategoriaRepository;
import com.br.demo.repository.SubcategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;
    private final CategoriaRepository categoriaRepository;

    public SubcategoriaService(SubcategoriaRepository subcategoriaRepository, CategoriaRepository categoriaRepository) {
        this.subcategoriaRepository = subcategoriaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public SubcategoriaResponseDTO criarSubcategoria(SubcategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).orElseThrow();
        Subcategoria subcategoria = Subcategoria.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .categoria(categoria)
                .build();
        subcategoria = subcategoriaRepository.save(subcategoria);

        return new SubcategoriaResponseDTO(
                subcategoria.getId(),
                subcategoria.getNome(),
                subcategoria.getDescricao(),
                subcategoria.getCategoria().getNome()
        );
    }

    public List<SubcategoriaResponseDTO> listarSubcategorias() {
        return subcategoriaRepository.findAll().stream()
                .map(subcategoria -> new SubcategoriaResponseDTO(
                        subcategoria.getId(),
                        subcategoria.getNome(),
                        subcategoria.getDescricao(),
                        subcategoria.getCategoria().getNome()
                ))
                .collect(Collectors.toList());
    }

    public SubcategoriaResponseDTO buscarPorId(UUID id) {
        return subcategoriaRepository.findById(id)
                .map(subcategoria -> new SubcategoriaResponseDTO(
                        subcategoria.getId(),
                        subcategoria.getNome(),
                        subcategoria.getDescricao(),
                        subcategoria.getCategoria().getNome()
                ))
                .orElse(null);
    }

    public SubcategoriaResponseDTO atualizarSubcategoria(UUID id, SubcategoriaRequestDTO dto) {
        Optional<Subcategoria> optional = subcategoriaRepository.findById(id);
        if (optional.isPresent()) {
            Subcategoria subcategoria = optional.get();
            subcategoria.setNome(dto.getNome());
            subcategoria.setDescricao(dto.getDescricao());
            Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).orElse(null);
            if (categoria != null) {
                subcategoria.setCategoria(categoria);
            }
            subcategoria = subcategoriaRepository.save(subcategoria);

            return new SubcategoriaResponseDTO(
                    subcategoria.getId(),
                    subcategoria.getNome(),
                    subcategoria.getDescricao(),
                    subcategoria.getCategoria().getNome()
            );
        }
        return null;
    }

    public void excluirSubcategoria(UUID id) {
        subcategoriaRepository.deleteById(id);
    }
}
