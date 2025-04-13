package com.br.demo.controller;

import com.br.demo.dto.request.PagamentoRequestDTO;
import com.br.demo.dto.response.PagamentoResponseDTO;
import com.br.demo.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criarPagamento(@RequestBody PagamentoRequestDTO dto) {
        return ResponseEntity.ok(pagamentoService.criarPagamento(dto));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> atualizarPagamento(@PathVariable UUID id, @RequestBody PagamentoRequestDTO dto) {
        return ResponseEntity.ok(pagamentoService.atualizarPagamento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagamento(@PathVariable UUID id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
