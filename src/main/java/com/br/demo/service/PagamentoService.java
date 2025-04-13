package com.br.demo.service;

import com.br.demo.dto.request.PagamentoRequestDTO;
import com.br.demo.dto.response.PagamentoResponseDTO;
import com.br.demo.model.Pagamento;
import com.br.demo.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public PagamentoResponseDTO criarPagamento(PagamentoRequestDTO dto) {
        Pagamento pagamento = Pagamento.builder()
                .formaPagamento(dto.getFormaPagamento())
                .build();

        pagamento = pagamentoRepository.save(pagamento);

        return PagamentoResponseDTO.builder()
                .id(pagamento.getId())
                .formaPagamento(pagamento.getFormaPagamento())
                .build();
    }

    public List<PagamentoResponseDTO> listarPagamentos() {
        return pagamentoRepository.findAll().stream()
                .map(pagamento -> PagamentoResponseDTO.builder()
                        .id(pagamento.getId())
                        .formaPagamento(pagamento.getFormaPagamento())
                        .build())
                .collect(Collectors.toList());
    }

    public PagamentoResponseDTO buscarPorId(UUID id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);
        return pagamento.map(p -> PagamentoResponseDTO.builder()
                        .id(p.getId())
                        .formaPagamento(p.getFormaPagamento())
                        .build())
                .orElse(null);
    }

    public PagamentoResponseDTO atualizarPagamento(UUID id, PagamentoRequestDTO dto) {
        Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(id);
        if (pagamentoOptional.isPresent()) {
            Pagamento pagamento = pagamentoOptional.get();
            pagamento.setFormaPagamento(dto.getFormaPagamento());
            pagamento = pagamentoRepository.save(pagamento);

            return PagamentoResponseDTO.builder()
                    .id(pagamento.getId())
                    .formaPagamento(pagamento.getFormaPagamento())
                    .build();
        }
        return null;
    }

    public void excluirPagamento(UUID id) {
        pagamentoRepository.deleteById(id);
    }
}
