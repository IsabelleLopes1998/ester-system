package com.br.demo.controller;

import com.br.demo.dto.request.ClienteRequestDTO;
import com.br.demo.dto.response.ClienteResponseDTO;
import com.br.demo.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ClienteController {

        private final ClienteService clienteService;

        public ClienteController(ClienteService clienteService) {
            this.clienteService = clienteService;
        }

        @GetMapping("/listaDeClientes")
        public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
            return ResponseEntity.ok(clienteService.listarClientes());
        }

        @GetMapping("/buscarPorId/{id}")
        public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
            return ResponseEntity.ok(clienteService.buscarPorId(id));
        }

        @PostMapping("/salvarCliente")
        public ResponseEntity<ClienteResponseDTO> criarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
            ClienteResponseDTO response = clienteService.criarCliente(clienteRequestDTO);
            return ResponseEntity.status(201).body(response);
        }

        @PutMapping("/atualizarCliente/{id}")
        public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
            return ResponseEntity.ok(clienteService.atualizarCliente(id, clienteRequestDTO));
        }


    @DeleteMapping("/excluirCliente/{id}")
        public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
            clienteService.excluirCliente(id);
            return ResponseEntity.noContent().build();
        }
}

