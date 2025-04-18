package com.br.demo.service;

import com.br.demo.dto.request.AcertoEstoqueRequestDTO;
import com.br.demo.dto.response.AcertoEstoqueResponseDTO;
import com.br.demo.model.AcertoEstoque;
import com.br.demo.model.Usuario;
import com.br.demo.repository.AcertoEstoqueRepository;
import com.br.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AcertoEstoqueService {
	
	private final AcertoEstoqueRepository repository;
	private final UsuarioRepository usuarioRepository;
	
	public AcertoEstoqueService(AcertoEstoqueRepository repository, UsuarioRepository usuarioRepository) {
		this.repository = repository;
		this.usuarioRepository = usuarioRepository;
	}
	
	public List<AcertoEstoqueResponseDTO> listar() {
		return repository.findAll().stream()
					   .map(this::toDTO)
					   .collect(Collectors.toList());
	}
	
	public AcertoEstoqueResponseDTO buscarPorId(UUID id) {
		return repository.findById(id)
					   .map(this::toDTO)
					   .orElse(null);
	}
	
	public AcertoEstoqueResponseDTO criar(AcertoEstoqueRequestDTO dto) {
		Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
								  .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		AcertoEstoque acerto = AcertoEstoque.builder()
									   .data(dto.getData())
									   .tipoAcerto(dto.getTipoAcerto())
									   .motivo(dto.getMotivo())
									   .usuario(usuario)
									   .build();
		
		return toDTO(repository.save(acerto));
	}
	
	public AcertoEstoqueResponseDTO atualizar(UUID id, AcertoEstoqueRequestDTO dto) {
		AcertoEstoque acerto = repository.findById(id)
									   .orElseThrow(() -> new IllegalArgumentException("Acerto não encontrado"));
		
		Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
								  .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		acerto.setData(dto.getData());
		acerto.setTipoAcerto(dto.getTipoAcerto());
		acerto.setMotivo(dto.getMotivo());
		acerto.setUsuario(usuario);
		
		return toDTO(repository.save(acerto));
	}
	
	public void excluir(UUID id) {
		repository.deleteById(id);
	}
	
	private AcertoEstoqueResponseDTO toDTO(AcertoEstoque acerto) {
		return new AcertoEstoqueResponseDTO(
				acerto.getId(),
				acerto.getData(),
				acerto.getTipoAcerto(),
				acerto.getMotivo(),
				acerto.getUsuario().getId()
		);
	}
}