package com.br.demo.controller;

import com.br.demo.dto.request.MovimentacaoItemRequestDTO;
import com.br.demo.dto.response.MovimentacaoItemResponseDTO;
import com.br.demo.model.Usuario;
import com.br.demo.service.MovimentacaoItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/acerto-itens")
public class MovimentacaoItemController {
	
	private final MovimentacaoItemService service;
	
	public MovimentacaoItemController (MovimentacaoItemService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<MovimentacaoItemResponseDTO>> listar() {
		return ResponseEntity.ok(service.listar());
	}
	
	@PostMapping
	public ResponseEntity<MovimentacaoItemResponseDTO> criar(@RequestBody MovimentacaoItemRequestDTO dto, @AuthenticationPrincipal Usuario usuario) {
		System.out.println(usuario.getUsername());
		return ResponseEntity.ok(service.criar(dto,usuario));
	}
	
	@DeleteMapping("/{idProduto}/{idAcerto}")
	public ResponseEntity<Void> excluir(@PathVariable UUID idAcerto) {
		service.excluir(idAcerto);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MovimentacaoItemResponseDTO> buscarPorId(@PathVariable UUID id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
}