package com.br.demo.exception;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String message) {
        super(message);
    }
}