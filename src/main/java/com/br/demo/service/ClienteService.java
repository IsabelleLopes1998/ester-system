package com.br.demo.service;

import com.br.demo.dto.request.ClienteRequestDTO;
import com.br.demo.dto.response.ClienteResponseDTO;
import com.br.demo.model.Cliente;
import com.br.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getDateBirth(),
                        cliente.getEmail(),
                        cliente.getRua(),
                        cliente.getNumero(),
                        cliente.getComplemento(),
                        cliente.getCep()))
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(UUID id) {
        return clienteRepository.findById(id)
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getDateBirth(),
                        cliente.getEmail(),
                        cliente.getRua(),
                        cliente.getNumero(),
                        cliente.getComplemento(),
                        cliente.getCep()))
                .orElse(null);
    }

    public ClienteResponseDTO criarCliente(ClienteRequestDTO dto) {
        Cliente cliente = Cliente.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .dateBirth(dto.getDateBirth())
                .email(dto.getEmail())
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .build();

        cliente = clienteRepository.save(cliente);

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getDateBirth(),
                cliente.getEmail(),
                cliente.getRua(),
                cliente.getNumero(),
                cliente.getComplemento(),
                cliente.getCep());
    }

    public ClienteResponseDTO atualizarCliente(UUID id, ClienteRequestDTO dto) {
        Optional<Cliente> optional = clienteRepository.findById(id);
        if (optional.isPresent()) {
            Cliente cliente = optional.get();
            cliente.setNome(dto.getNome());
            cliente.setCpf(dto.getCpf());
            cliente.setDateBirth(dto.getDateBirth());
            cliente.setEmail(dto.getEmail());
            cliente.setRua(dto.getRua());
            cliente.setNumero(dto.getNumero());
            cliente.setComplemento(dto.getComplemento());
            cliente.setCep(dto.getCep());

            return new ClienteResponseDTO(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getDateBirth(),
                    cliente.getEmail(),
                    cliente.getRua(),
                    cliente.getNumero(),
                    cliente.getComplemento(),
                    cliente.getCep());
        }
        return null;
    }

    public void excluirCliente(UUID id) {
        clienteRepository.deleteById(id);
    }
}