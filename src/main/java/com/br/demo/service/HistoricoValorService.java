package com.br.demo.service;

import com.br.demo.dto.request.HistoricoValorRequestDTO;
import com.br.demo.dto.request.ProdutoRequestDTO;
import com.br.demo.dto.response.CategoriaResponseDTO;
import com.br.demo.dto.response.HistoricoValorResponseDTO;
import com.br.demo.dto.response.ProdutoResponseDTO;
import com.br.demo.model.Categoria;
import com.br.demo.model.HistoricoValor;
import com.br.demo.model.Produto;
import com.br.demo.repository.HistoricoValorRepository;
import com.br.demo.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class HistoricoValorService {

    private HistoricoValorRepository historicoValorRepository;

    private ProdutoRepository produtoRepository;


    public List<HistoricoValorResponseDTO> listarHistoricoValor(){
        return historicoValorRepository.findAll().stream()
                .map(historicoValor -> new HistoricoValorResponseDTO(historicoValor.getId(), historicoValor.getData(), historicoValor.getValor(), historicoValor.getProduto().getNome()))
                .collect(Collectors.toList());
    }

    public HistoricoValorResponseDTO buscarPorId(Long id){
        return historicoValorRepository.findById(id)
                .map(historicoValor -> new HistoricoValorResponseDTO(historicoValor.getId(), historicoValor.getData(), historicoValor.getValor(), historicoValor.getProduto().getNome()))
                .orElse(null) ;
    }

    public HistoricoValorResponseDTO criarHistorico(HistoricoValorRequestDTO historicoValorRequestDTO){
        Produto produto = produtoRepository.findById(historicoValorRequestDTO.getIdProduto())
                .orElse(null);
        if(produto == null){
            return null;
        }
        HistoricoValor historicoValor = new HistoricoValor(historicoValorRequestDTO.getData(), historicoValorRequestDTO.getValor(), produto);
        historicoValor = historicoValorRepository.save(historicoValor);

        return new HistoricoValorResponseDTO(historicoValor.getId(), historicoValor.getData(), historicoValor.getValor(), historicoValor.getProduto().getNome());
    }
}


