package com.br.demo.controller;

import com.br.demo.dto.request.HistoricoValorRequestDTO;
import com.br.demo.dto.response.HistoricoValorResponseDTO;
import com.br.demo.service.HistoricoValorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/historicovalor")
public class HistoricoValorController {

    private final HistoricoValorService historicoValorService;

    public HistoricoValorController(HistoricoValorService historicoValorService) {
        this.historicoValorService = historicoValorService;
    }

    @GetMapping
    public ResponseEntity<List<HistoricoValorResponseDTO>> listarHistoricoValor() {
        return ResponseEntity.ok(historicoValorService.listarHistoricoValor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoValorResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(historicoValorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<HistoricoValorResponseDTO> criarHistoricoValor(@RequestBody HistoricoValorRequestDTO dto) {
        return ResponseEntity.ok(historicoValorService.criarHistoricoValor(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoValorResponseDTO> atualizarHistoricoValor(@PathVariable UUID id, @RequestBody HistoricoValorRequestDTO dto) {
        return ResponseEntity.ok(historicoValorService.atualizarHistoricoValor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirHistorico(@PathVariable UUID id) {
        historicoValorService.excluirHistorico(id);
        return ResponseEntity.noContent().build();
    }
}
