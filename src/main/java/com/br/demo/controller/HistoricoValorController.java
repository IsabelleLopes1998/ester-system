package com.br.demo.controller;


import com.br.demo.dto.request.HistoricoValorRequestDTO;
import com.br.demo.dto.response.HistoricoValorResponseDTO;
import com.br.demo.service.HistoricoValorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historicovalor")
public class HistoricoValorController {
    private final HistoricoValorService historicoValorService;


    public HistoricoValorController(HistoricoValorService historicoValorService) {
        this.historicoValorService = historicoValorService;
    }

    @GetMapping
    public ResponseEntity<List<HistoricoValorResponseDTO>> listarHistoricoValor(){
        return ResponseEntity.ok(historicoValorService.listarHistoricoValor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoValorResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(historicoValorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<HistoricoValorResponseDTO> criarHistoricoValor(@RequestBody HistoricoValorRequestDTO historicoValorRequestDTO){
        return  ResponseEntity.ok(historicoValorService.criarHistoricoValor(historicoValorRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoValorResponseDTO> atualizarHistoricoValor(@PathVariable Long id, @RequestBody HistoricoValorRequestDTO historicoValorRequestDTO){
        return ResponseEntity.ok(historicoValorService.atualizarHistoricoValor(id, historicoValorRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirHistorico(@PathVariable Long id){
        historicoValorService.excluirHistorico(id);
        return ResponseEntity.noContent().build(); //no content: não envia o retorno, build: constroe o método
    }
}
