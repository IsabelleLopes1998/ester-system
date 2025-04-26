package com.br.demo.controller;

import com.br.demo.dto.response.VendaItemResponseDTO;
import com.br.demo.service.VendaItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda-itens")
public class VendaItemController {
	
	private final VendaItemService vendaItemService;
	
	public VendaItemController(VendaItemService vendaItemService) {
		this.vendaItemService = vendaItemService;
	}
	
	@GetMapping
	public ResponseEntity<List<VendaItemResponseDTO>> listar() {
		return ResponseEntity.ok(vendaItemService.listar());
	}
}