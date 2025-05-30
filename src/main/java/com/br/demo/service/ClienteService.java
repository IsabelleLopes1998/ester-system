package com.br.demo.service;

import com.br.demo.dto.request.ClienteRequestDTO;
import com.br.demo.dto.response.ClienteResponseDTO;
import com.br.demo.model.Cliente;
import com.br.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                        cliente.getDataNascimento(),
                        cliente.getEmail(),
                        cliente.getRua(),
                        cliente.getNumero(),
                        cliente.getComplemento(),
                        cliente.getCep(),
                        cliente.getPrimeiroTelefone(),
                        cliente.getSegundoTelefone()))
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getDataNascimento(),
                        cliente.getEmail(),
                        cliente.getRua(),
                        cliente.getNumero(),
                        cliente.getComplemento(),
                        cliente.getCep(),
                        cliente.getPrimeiroTelefone(),
                        cliente.getSegundoTelefone()))
                .orElse(null);
    }
    
    public ClienteResponseDTO criarCliente(ClienteRequestDTO dto) {
        System.out.println("Recebido DTO: " + dto); //debug
        
        Cliente cliente = Cliente.builder()
                                  .nome(dto.getNome())
                                  .cpf(dto.getCpf())
                                  .dataNascimento(dto.getDataNascimento())
                                  .email(dto.getEmail())
                                  .rua(dto.getRua())
                                  .numero(dto.getNumero())
                                  .complemento(dto.getComplemento())
                                  .cep(dto.getCep())
                                  .primeiroTelefone(dto.getPrimeiroTelefone())
                                  .segundoTelefone(dto.getSegundoTelefone())
                                  .build();
        
        System.out.println("Cliente montado: " + cliente); // ← Adicionado para debug
        
        cliente = clienteRepository.save(cliente); // ← Aqui o erro acontece
        
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getEmail(),
                cliente.getRua(),
                cliente.getNumero(),
                cliente.getComplemento(),
                cliente.getCep(),
                cliente.getPrimeiroTelefone(),
                cliente.getSegundoTelefone());
    }
    
    
    public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO dto) {
        Optional<Cliente> optional = clienteRepository.findById(id);
        if (optional.isPresent()) {
            Cliente cliente = optional.get();
            cliente.setNome(dto.getNome());
            cliente.setCpf(dto.getCpf());
            cliente.setDataNascimento(dto.getDataNascimento());
            cliente.setEmail(dto.getEmail());
            cliente.setRua(dto.getRua());
            cliente.setNumero(dto.getNumero());
            cliente.setComplemento(dto.getComplemento());
            cliente.setPrimeiroTelefone(dto.getPrimeiroTelefone());
            cliente.setSegundoTelefone(dto.getSegundoTelefone());
            cliente.setCep(dto.getCep());
            clienteRepository.save(cliente);
            return new ClienteResponseDTO(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getDataNascimento(),
                    cliente.getEmail(),
                    cliente.getRua(),
                    cliente.getNumero(),
                    cliente.getComplemento(),
                    cliente.getCep(),
                    cliente.getPrimeiroTelefone(),
                    cliente.getSegundoTelefone());
        }
        return null;
    }

    public void excluirCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}