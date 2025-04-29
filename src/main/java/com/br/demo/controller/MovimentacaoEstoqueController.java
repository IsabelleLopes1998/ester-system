package com.br.demo.controller;

import com.br.demo.dto.request.MovimentacaoEstoqueRequestDTO;
import com.br.demo.dto.response.MovimentacaoEstoqueResponseDTO;
import com.br.demo.model.Usuario;
import com.br.demo.service.MovimentacaoEstoque;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/acerto-itens")
public class MovimentacaoEstoqueController {
	
	private final MovimentacaoEstoque service;
	
	public MovimentacaoEstoqueController (MovimentacaoEstoque service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<MovimentacaoEstoqueResponseDTO>> listar() {
		return ResponseEntity.ok(service.listar());
	}
	
	@PostMapping
	public ResponseEntity<MovimentacaoEstoqueResponseDTO> criar(@RequestBody MovimentacaoEstoqueRequestDTO dto, @AuthenticationPrincipal Usuario usuario) {
		System.out.println(usuario.getUsername());
		return ResponseEntity.ok(service.criar(dto,usuario));
	}
	
	@DeleteMapping("/{idProduto}/{idAcerto}")
	public ResponseEntity<Void> excluir(@PathVariable UUID idAcerto) {
		service.excluir(idAcerto);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MovimentacaoEstoqueResponseDTO> buscarPorId(@PathVariable UUID id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
}