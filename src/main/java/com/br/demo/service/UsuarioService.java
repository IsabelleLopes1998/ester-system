package com.br.demo.service;

import com.br.demo.dto.request.UsuarioRequestDTO;
import com.br.demo.dto.response.UsuarioResponseDTO;
import com.br.demo.model.Cargo;
import com.br.demo.model.Usuario;
import com.br.demo.repository.CargoRepository;
import com.br.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        Cargo cargo = cargoRepository.findById(dto.getIdCargo()).orElseThrow();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getSenha());
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .dataNascimento(dto.getDataNascimento())
                .username(dto.getUsername())
                .senha(encodedPassword)
                .telefonePrincipal(dto.getTelefonePrincipal())
                .telefoneSecundario(dto.getTelefoneSecundario())
                .cargo(cargo)
                .build();
        usuario = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                usuario.getUsername(),
                usuario.getTelefonePrincipal(),
                usuario.getTelefoneSecundario(),
                usuario.getCargo().getNome()
        );
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getCpf(),
                        usuario.getDataNascimento(),
                        usuario.getUsername(),
                        usuario.getTelefonePrincipal(),
                        usuario.getTelefoneSecundario(),
                        usuario.getCargo().getNome()
                ))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getCpf(),
                        usuario.getDataNascimento(),
                        usuario.getUsername(),
                        usuario.getTelefonePrincipal(),
                        usuario.getTelefoneSecundario(),
                        usuario.getCargo().getNome()
                ))
                .orElse(null);
    }

    public UsuarioResponseDTO atualizarUsuario(UUID id, UsuarioRequestDTO dto) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.setNome(dto.getNome());
            usuario.setCpf(dto.getCpf());
            usuario.setDataNascimento(dto.getDataNascimento());
            usuario.setUsername(dto.getUsername());
            usuario.setSenha(dto.getSenha());
            usuario.setTelefonePrincipal(dto.getTelefonePrincipal());
            usuario.setTelefoneSecundario(dto.getTelefoneSecundario());

            Cargo cargo = cargoRepository.findById(dto.getIdCargo()).orElse(null);
            if (cargo != null) {
                usuario.setCargo(cargo);
            }

            usuario = usuarioRepository.save(usuario);

            return new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getCpf(),
                    usuario.getDataNascimento(),
                    usuario.getUsername(),
                    usuario.getTelefonePrincipal(),
                    usuario.getTelefoneSecundario(),
                    usuario.getCargo().getNome()
            );
        }
        return null;
    }

    public void excluirUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }
}
