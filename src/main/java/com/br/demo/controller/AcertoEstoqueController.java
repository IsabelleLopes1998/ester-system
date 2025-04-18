package com.br.demo.controller;

import com.br.demo.dto.request.AcertoEstoqueRequestDTO;
import com.br.demo.dto.response.AcertoEstoqueResponseDTO;
import com.br.demo.service.AcertoEstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/acertos-estoque")
public class AcertoEstoqueController {
	
	private final AcertoEstoqueService service;
	
	public AcertoEstoqueController(AcertoEstoqueService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<AcertoEstoqueResponseDTO>> listar() {
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AcertoEstoqueResponseDTO> buscarPorId(@PathVariable UUID id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<AcertoEstoqueResponseDTO> criar(@RequestBody AcertoEstoqueRequestDTO dto) {
		return ResponseEntity.ok(service.criar(dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AcertoEstoqueResponseDTO> atualizar(@PathVariable UUID id, @RequestBody AcertoEstoqueRequestDTO dto) {
		return ResponseEntity.ok(service.atualizar(id, dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable UUID id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
}