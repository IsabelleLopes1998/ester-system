package com.br.demo.service;

import com.br.demo.dto.response.VendaItemResponseDTO;
import com.br.demo.repository.VendaItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaItemService {
	
	private final VendaItemRepository vendaItemRepository;
	
	public VendaItemService(VendaItemRepository vendaItemRepository) {
		this.vendaItemRepository = vendaItemRepository;
	}
	
	public List<VendaItemResponseDTO> listar() {
		return vendaItemRepository.findAll().stream()
					   .map(item -> VendaItemResponseDTO.builder()
											.nomeProduto(item.getProduto().getNome())
											.quantidadeVenda(item.getQuantidadeVenda())
											.build())
					   .collect(Collectors.toList());
	}
}