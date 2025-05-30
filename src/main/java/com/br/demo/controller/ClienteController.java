package com.br.demo.controller;

import com.br.demo.dto.request.ClienteRequestDTO;
import com.br.demo.dto.response.ClienteResponseDTO;
import com.br.demo.model.Cliente;
import com.br.demo.repository.ClienteRepository;
import com.br.demo.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ClienteController {

        private final ClienteService clienteService;
        private final ClienteRepository clienteRepository;


        public ClienteController(ClienteService clienteService, ClienteRepository clienteRepository) {
            this.clienteService = clienteService;
            this.clienteRepository = clienteRepository;
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

    @GetMapping("/clientes/exists-by-cpf")
    public ResponseEntity<Boolean> verificarCpf(@RequestParam String cpf, @RequestParam(required = false) Long id) {
        try {
            Optional<Cliente> existente = clienteRepository.findByCpf(cpf);
            boolean duplicado = existente.isPresent() && !existente.get().getId().equals(id);
            return ResponseEntity.ok(duplicado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }


    @GetMapping("/clientes/exists-by-email")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email, @RequestParam(required = false) Long id) {
        Optional<Cliente> existente = clienteRepository.findByEmail(email);

        boolean duplicado = existente.isPresent() && !existente.get().getId().equals(id);
        return ResponseEntity.ok(duplicado);
    }



}

