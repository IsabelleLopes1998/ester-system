package com.br.demo.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "minha-senha-secreta"; // Use env var in prod
    private final long EXPIRATION_TIME = 86400000; // 1 dia

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    public String gerarToken(org.springframework.security.core.userdetails.UserDetails usuario) {
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(getAlgorithm());
    }

    public String getUsernameFromToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            return null; // token inválido
        }
    }

    public boolean isTokenValido(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}