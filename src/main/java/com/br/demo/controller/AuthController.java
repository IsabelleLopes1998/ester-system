package com.br.demo.controller;

import com.br.demo.dto.request.AuthRequestDTO;
import com.br.demo.dto.response.AuthResponseDTO;
import com.br.demo.providers.JwtTokenProvider;
import com.br.demo.service.AuthService;
import com.br.demo.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          AuthService authService,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getSenha());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = jwtTokenProvider.gerarToken(usuario);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }


    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token ausente ou mal formatado");
        }

        String token = authHeader.replace("Bearer ", "");

        if (jwtTokenProvider.isTokenValido(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            return ResponseEntity.ok("Token válido para o usuário: " + username);
        } else {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }
    }
}

