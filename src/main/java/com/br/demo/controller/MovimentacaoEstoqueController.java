package com.br.demo.controller;

import com.br.demo.dto.request.AcertoItemRequestDTO;
import com.br.demo.dto.response.AcertoItemResponseDTO;
import com.br.demo.model.Usuario;
import com.br.demo.service.AcertoItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/acerto-itens")
public class AcertoItemController {
	
	private final AcertoItemService service;
	
	public AcertoItemController(AcertoItemService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<AcertoItemResponseDTO>> listar() {
		return ResponseEntity.ok(service.listar());
	}
	
	@PostMapping
	public ResponseEntity<AcertoItemResponseDTO> criar(@RequestBody AcertoItemRequestDTO dto, @AuthenticationPrincipal Usuario usuario) {
		System.out.println(usuario.getUsername());
		return ResponseEntity.ok(service.criar(dto,usuario));
	}
	
	@DeleteMapping("/{idProduto}/{idAcerto}")
	public ResponseEntity<Void> excluir(@PathVariable UUID idAcerto) {
		service.excluir(idAcerto);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AcertoItemResponseDTO> buscarPorId(@PathVariable UUID id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
}