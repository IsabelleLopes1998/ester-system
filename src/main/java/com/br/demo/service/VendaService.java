package com.br.demo.service;

import com.br.demo.dto.request.VendaRequestDTO;
import com.br.demo.dto.response.VendaResponseDTO;
import com.br.demo.model.Cliente;
import com.br.demo.model.Pagamento;
import com.br.demo.model.Usuario;
import com.br.demo.model.Venda;
import com.br.demo.repository.ClienteRepository;
import com.br.demo.repository.PagamentoRepository;
import com.br.demo.repository.UsuarioRepository;
import com.br.demo.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VendaService {
	
	private final VendaRepository vendaRepository;
	private final UsuarioRepository usuarioRepository;
	private final PagamentoRepository pagamentoRepository;
	private final ClienteRepository clienteRepository;
	
	public VendaService(
			VendaRepository vendaRepository,
			UsuarioRepository usuarioRepository,
			PagamentoRepository pagamentoRepository ,
			ClienteRepository clienteRepository) {
		this.vendaRepository = vendaRepository;
		this.usuarioRepository = usuarioRepository;
		this.pagamentoRepository = pagamentoRepository;
		this.clienteRepository = clienteRepository;
	}
	
	public List<VendaResponseDTO> getAllVendas() {
		return vendaRepository.findAll().stream()
					   .map(this::toResponseDTO)
					   .collect(Collectors.toList());
	}
	
	public VendaResponseDTO getById(UUID id) {
		return vendaRepository.findById(id)
					   .map(this::toResponseDTO)
					   .orElse(null);
	}
	
	public VendaResponseDTO createVenda(VendaRequestDTO dto) {
		Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
		Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElse(null);
		Pagamento pagamento = pagamentoRepository.findById(dto.getIdPagamento()).orElse(null);
		
		if (usuario == null || cliente == null || pagamento == null) {
			return null;
		}
		
		Venda venda = Venda.builder()
							  .data(dto.getData())
							  .usuario(usuario)
							  .cliente(cliente)
							  .pagamento(pagamento)
							  .build();
		
		venda = vendaRepository.save(venda);
		
		return toResponseDTO(venda);
	}
	
	public VendaResponseDTO updateVenda(UUID id, VendaRequestDTO dto) {
		Optional<Venda> optionalVenda = vendaRepository.findById(id);
		
		if (optionalVenda.isPresent()) {
			Venda venda = optionalVenda.get();
			
			Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
			Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElse(null);
			Pagamento pagamento = pagamentoRepository.findById(dto.getIdPagamento()).orElse(null);
			
			if (usuario != null) venda.setUsuario(usuario);
			if (cliente != null) venda.setCliente(cliente);
			if (pagamento != null) venda.setPagamento(pagamento);
			
			venda.setData(dto.getData());
			
			venda = vendaRepository.save(venda);
			
			return toResponseDTO(venda);
		}
		
		return null;
	}
	
	public void deleteVenda(UUID id) {
		vendaRepository.deleteById(id);
	}
	
	private VendaResponseDTO toResponseDTO(Venda venda) {
		return VendaResponseDTO.builder()
					   .id(venda.getId())
					   .idUsuario(venda.getUsuario().getId())
					   .idCliente(venda.getCliente().getId())
					   .idPagamento(venda.getPagamento().getId())
					   .dataVenda(venda.getData())
					   .build();
	}
}
