package com.br.demo.service;

import com.br.demo.dto.request.HistoricoValorRequestDTO;
import com.br.demo.dto.response.HistoricoValorResponseDTO;
import com.br.demo.model.HistoricoValor;
import com.br.demo.model.Produto;
import com.br.demo.repository.HistoricoValorRepository;
import com.br.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HistoricoValorService {

    private final HistoricoValorRepository historicoValorRepository;
    private final ProdutoRepository produtoRepository;

    public HistoricoValorService(HistoricoValorRepository historicoValorRepository, ProdutoRepository produtoRepository) {
        this.historicoValorRepository = historicoValorRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<HistoricoValorResponseDTO> listarHistoricoValor() {
        return historicoValorRepository.findAll().stream()
                .map(historicoValor -> new HistoricoValorResponseDTO(
                        historicoValor.getId(),
                        historicoValor.getData(),
                        historicoValor.getValor(),
                        historicoValor.getProduto().getNome()))
                .collect(Collectors.toList());
    }

    public HistoricoValorResponseDTO buscarPorId(UUID id) {
        return historicoValorRepository.findById(id)
                .map(historicoValor -> new HistoricoValorResponseDTO(
                        historicoValor.getId(),
                        historicoValor.getData(),
                        historicoValor.getValor(),
                        historicoValor.getProduto().getNome()))
                .orElse(null);
    }

    public HistoricoValorResponseDTO criarHistoricoValor(HistoricoValorRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProduto().getId())
                .orElse(null);

        if (produto == null) {
            return null;
        }

        HistoricoValor historicoValor = HistoricoValor.builder()
                .data(dto.getData())
                .valor(dto.getValor())
                .produto(produto)
                .build();

        historicoValor = historicoValorRepository.save(historicoValor);

        return new HistoricoValorResponseDTO(
                historicoValor.getId(),
                historicoValor.getData(),
                historicoValor.getValor(),
                historicoValor.getProduto().getNome());
    }

    public HistoricoValorResponseDTO atualizarHistoricoValor(UUID id, HistoricoValorRequestDTO dto) {
        Optional<HistoricoValor> optional = historicoValorRepository.findById(id);

        if (optional.isPresent()) {
            HistoricoValor historicoValor = optional.get();
            historicoValor.setData(dto.getData());
            historicoValor.setValor(dto.getValor());

            Produto produto = produtoRepository.findById(dto.getProduto().getId()).orElse(null);
            if (produto != null) {
                historicoValor.setProduto(produto);
            }

            historicoValorRepository.save(historicoValor);

            return new HistoricoValorResponseDTO(
                    historicoValor.getId(),
                    historicoValor.getData(),
                    historicoValor.getValor(),
                    historicoValor.getProduto().getNome());
        }
        return null;
    }

    public void excluirHistorico(UUID id) {
        historicoValorRepository.deleteById(id);
    }
}
