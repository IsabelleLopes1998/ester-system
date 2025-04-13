package com.br.demo.service;


import com.br.demo.dto.request.CargoRequestDTO;
import com.br.demo.dto.response.CargoResponseDTO;
import com.br.demo.model.Cargo;
import com.br.demo.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public CargoResponseDTO criarCargo(CargoRequestDTO dto) {
        Cargo cargo = Cargo.builder()
                .nome(dto.getNome())
                .build();
        cargo = cargoRepository.save(cargo);

        return new CargoResponseDTO(cargo.getId(), cargo.getNome());
    }

    public List<CargoResponseDTO> listarCargos() {
        return cargoRepository.findAll().stream()
                .map(cargo -> new CargoResponseDTO(cargo.getId(), cargo.getNome()))
                .collect(Collectors.toList());
    }

    public CargoResponseDTO buscarPorId(UUID id) {
        return cargoRepository.findById(id)
                .map(cargo -> new CargoResponseDTO(cargo.getId(), cargo.getNome()))
                .orElse(null);
    }

    public CargoResponseDTO atualizarCargo(UUID id, CargoRequestDTO dto) {
        Optional<Cargo> optional = cargoRepository.findById(id);
        if (optional.isPresent()) {
            Cargo cargo = optional.get();
            cargo.setNome(dto.getNome());
            cargo = cargoRepository.save(cargo);

            return new CargoResponseDTO(cargo.getId(), cargo.getNome());
        }
        return null;
    }

    public void excluirCargo(UUID id) {
        cargoRepository.deleteById(id);
    }
}
