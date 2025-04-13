package com.br.demo.controller;

import com.br.demo.dto.request.CargoRequestDTO;
import com.br.demo.dto.response.CargoResponseDTO;
import com.br.demo.service.CargoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public ResponseEntity<CargoResponseDTO> criarCargo(@RequestBody CargoRequestDTO dto) {
        return ResponseEntity.ok(cargoService.criarCargo(dto));
    }

    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> listarCargos() {
        return ResponseEntity.ok(cargoService.listarCargos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(cargoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> atualizarCargo(@PathVariable UUID id, @RequestBody CargoRequestDTO dto) {
        return ResponseEntity.ok(cargoService.atualizarCargo(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCargo(@PathVariable UUID id) {
        cargoService.excluirCargo(id);
        return ResponseEntity.noContent().build();
    }
}
