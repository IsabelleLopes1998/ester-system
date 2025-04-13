package com.br.demo.controller;

import com.br.demo.dto.response.CompraItemResponseDTO;
import com.br.demo.service.CompraItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra-itens")
public class CompraItemController {

    private final CompraItemService compraItemService;

    public CompraItemController(CompraItemService compraItemService) {
        this.compraItemService = compraItemService;
    }

    @GetMapping
    public ResponseEntity<List<CompraItemResponseDTO>> listarCompraItens() {
        return ResponseEntity.ok(compraItemService.listarCompraItens());
    }
}
