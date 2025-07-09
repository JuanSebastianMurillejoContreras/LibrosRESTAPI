package com.libros.librosrestapi.Libro.exception;

import lombok.Getter;

@Getter
public class LibroNotFoundException extends RuntimeException {

    private String code;

    public LibroNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}