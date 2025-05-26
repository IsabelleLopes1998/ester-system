package com.br.demo.controller;

import com.br.demo.dto.request.CompraRequestDTO;
import com.br.demo.dto.response.CompraResponseDTO;
import com.br.demo.model.Usuario;
import com.br.demo.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> criarCompra(@RequestBody CompraRequestDTO compraRequestDTO, @AuthenticationPrincipal Usuario usuario) {
        CompraResponseDTO compra = compraService.criarCompra(compraRequestDTO, usuario);
        return ResponseEntity.ok(compra);
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listarCompras() {
        return ResponseEntity.ok(compraService.listarCompras());
    }
}
