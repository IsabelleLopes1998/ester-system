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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoricoValorService {

    @Autowired
    private HistoricoValorRepository historicoValorRepository;
    @Autowired
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

    public HistoricoValorResponseDTO criarHistoricoValor(HistoricoValorRequestDTO historicoValorRequestDTO){
        Produto produto = produtoRepository.findById(historicoValorRequestDTO.getIdProduto())
                .orElse(null);
        if(produto == null){
            return null;
        }
        HistoricoValor historicoValor = new HistoricoValor(historicoValorRequestDTO.getData(), historicoValorRequestDTO.getValor(), produto);
        historicoValor = historicoValorRepository.save(historicoValor);

        return new HistoricoValorResponseDTO(historicoValor.getId(), historicoValor.getData(), historicoValor.getValor(), historicoValor.getProduto().getNome());
    }

    public HistoricoValorResponseDTO atualizarHistoricoValor(Long id, HistoricoValorRequestDTO historicoValorRequestDTO){
        Optional<HistoricoValor> optionalHistoricoValor = historicoValorRepository.findById(id);
        if(optionalHistoricoValor.isPresent()){
            HistoricoValor historicoValor = optionalHistoricoValor.get();

            historicoValor.setData(historicoValorRequestDTO.getData());
            historicoValor.setValor(historicoValorRequestDTO.getValor());

            Produto produto = produtoRepository.findById(historicoValorRequestDTO.getIdProduto()).orElse(null);
            if(produto != null){
                historicoValor.setProduto(produto);
            }
            historicoValorRepository.save(historicoValor);
            return new HistoricoValorResponseDTO(historicoValor.getId(), historicoValor.getData(), historicoValor.getValor(), historicoValor.getProduto().getNome());
        }
        return null;
    }

    public void excluirHistorico(Long id){
        historicoValorRepository.deleteById(id);
    }
}


