package com.api.bancoalpha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContaInexistenteException extends RuntimeException{
    public ContaInexistenteException(String message) {
        super(message);
    }
}
