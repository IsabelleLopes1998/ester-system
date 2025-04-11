package com.br.demo.service;

import com.br.demo.dto.request.CategoriaRequestDTO;
import com.br.demo.dto.response.CategoriaResponseDTO;
import com.br.demo.model.Categoria;
import com.br.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDTO> listarCategorias(){
        return categoriaRepository.findAll().stream()
                .map(categoria -> new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao()))
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id){
        return categoriaRepository.findById(id)
                .map(categoria -> new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao()))
                .orElse(null);
    }

    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaRequestDTO){
        Categoria categoria = new Categoria(null, categoriaRequestDTO.getNome(), categoriaRequestDTO.getDescricao());
        categoria = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if(optionalCategoria.isPresent()){
            Categoria categoria = optionalCategoria.get();
            categoria.setNome(categoriaRequestDTO.getNome());
            categoria.setDescricao(categoriaRequestDTO.getDescricao());
            return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
        }
        return  null;
    }

    public void excluirCategoria(Long id){
        categoriaRepository.deleteById(id);
    }
}
