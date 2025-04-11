package com.br.demo.controller;

import com.br.demo.dto.request.ClienteRequestDTO;
import com.br.demo.dto.response.ClienteResponseDTO;
import com.br.demo.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

        private final ClienteService clienteService;

        public ClienteController(ClienteService clienteService) {
            this.clienteService = clienteService;
        }

        @GetMapping
        public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
            return ResponseEntity.ok(clienteService.listarClientes());
        }

        @GetMapping("/{id}")
        public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable UUID id) {
            return ResponseEntity.ok(clienteService.buscarPorId(id));
        }

        @PostMapping
        public ResponseEntity<ClienteResponseDTO> criarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
            return ResponseEntity.ok(clienteService.criarCliente(clienteRequestDTO));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable UUID id, @RequestBody ClienteRequestDTO clienteRequestDTO) {
            return ResponseEntity.ok(clienteService.atualizarCliente(id, clienteRequestDTO));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> excluirCliente(@PathVariable UUID id) {
            clienteService.excluirCliente(id);
            return ResponseEntity.noContent().build();
        }
}

