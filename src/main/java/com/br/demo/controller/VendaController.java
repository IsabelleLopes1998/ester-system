package com.br.demo.controller;

import com.br.demo.dto.request.VendaRequestDTO;
import com.br.demo.dto.response.VendaResponseDTO;
import com.br.demo.model.Usuario;
import com.br.demo.model.Venda;
import com.br.demo.repository.VendaRepository;
import com.br.demo.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vendas")
public class VendaController {
	private final VendaService vendaService;

	
	public VendaController (VendaService vendaService) {
		this.vendaService = vendaService;
	}
	
	@PostMapping
	@Operation(summary = "Create venda")
	
	public VendaResponseDTO createVenda(@RequestBody VendaRequestDTO vendaRequestDTO, @AuthenticationPrincipal Usuario usuario){
		return vendaService.createVenda(vendaRequestDTO, usuario);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Search venda")
	
	public VendaResponseDTO getVenda(@PathVariable UUID id){
		return vendaService.getById(id);
	}
	
	@GetMapping()
	@Operation(summary = "Get all vendas")
	
	public List <VendaResponseDTO> getAllVendas(){
		return vendaService.getAllVendas();
	}
	
	@PutMapping("/{id}")
	public VendaResponseDTO updateVenda(@PathVariable UUID id, @RequestBody VendaRequestDTO vendaRequestDTO){
		return vendaService.updateVenda(id, vendaRequestDTO);
	}
}

