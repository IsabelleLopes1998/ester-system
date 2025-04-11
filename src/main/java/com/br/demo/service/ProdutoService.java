package com.br.demo.service;


import com.br.demo.dto.request.ProdutoRequestDTO;
import com.br.demo.dto.response.ProdutoResponseDTO;
import com.br.demo.model.Categoria;
import com.br.demo.model.Produto;
import com.br.demo.repository.CategoriaRepository;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;



    public List<ProdutoResponseDTO> listarProdutos(){
        return produtoRepository.findAll().stream()
                .map(produto -> new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome()))
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id){
       return produtoRepository.findById(id)
               .map(produto -> new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome()))
               .orElse(null) ;
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequestDTO){
        Categoria categoria = categoriaRepository.findById(produtoRequestDTO.getCategoriaId())
                .orElse(null);
        if(categoria == null){
            return null;
        }
        Produto produto = new Produto(produtoRequestDTO.getNome(), produtoRequestDTO.getPreco(), produtoRequestDTO.getNumeroSerie(), categoria);
        produto = produtoRepository.save(produto);

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome());
    }

    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO){
     Optional<Produto> optionalProduto = produtoRepository.findById(id);
     if(optionalProduto.isPresent()){
         Produto produto = optionalProduto.get();
         produto.setNome(produtoRequestDTO.getNome());
         produto.setPreco(produtoRequestDTO.getPreco());
         produto.setNumeroSerie(produtoRequestDTO.getNumeroSerie());
         Categoria categoria = categoriaRepository.findById(produtoRequestDTO.getCategoriaId()).orElse(null);
         if(categoria != null){
             produto.setCategoria(categoria);
         }
         produtoRepository.save(produto);
         return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome());
     }
      return null;
    }


    public void excluirProduto(Long id){
        produtoRepository.deleteById(id);
    }


}
