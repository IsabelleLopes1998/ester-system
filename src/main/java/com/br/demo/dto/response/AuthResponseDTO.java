package com.br.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;

    public String getToken() {
        return token;
    }
}