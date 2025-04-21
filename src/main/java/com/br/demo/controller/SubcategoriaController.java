package com.br.demo.controller;

import com.br.demo.dto.request.SubcategoriaRequestDTO;
import com.br.demo.dto.response.SubcategoriaResponseDTO;
import com.br.demo.service.SubcategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subcategorias")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class SubcategoriaController {

    private final SubcategoriaService subcategoriaService;

    public SubcategoriaController(SubcategoriaService subcategoriaService) {
        this.subcategoriaService = subcategoriaService;
    }

    @PostMapping("/salvarSubcategoria")
    public ResponseEntity<SubcategoriaResponseDTO> criarSubcategoria(@RequestBody SubcategoriaRequestDTO dto) {
        return ResponseEntity.ok(subcategoriaService.criarSubcategoria(dto));
    }

    @GetMapping("/listaDeSubcategorias")
    public ResponseEntity<List<SubcategoriaResponseDTO>> listarSubcategorias() {
        return ResponseEntity.ok(subcategoriaService.listarSubcategorias());
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<SubcategoriaResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(subcategoriaService.buscarPorId(id));
    }

    @PutMapping("/atualizarSubcategoria/{id}")
    public ResponseEntity<SubcategoriaResponseDTO> atualizarSubcategoria(@PathVariable UUID id, @RequestBody SubcategoriaRequestDTO dto) {
        return ResponseEntity.ok(subcategoriaService.atualizarSubcategoria(id, dto));
    }

    @DeleteMapping("/excluirSubcategoria/{id}")
    public ResponseEntity<Void> excluirSubcategoria(@PathVariable UUID id) {
        subcategoriaService.excluirSubcategoria(id);
        return ResponseEntity.noContent().build();
    }
}
