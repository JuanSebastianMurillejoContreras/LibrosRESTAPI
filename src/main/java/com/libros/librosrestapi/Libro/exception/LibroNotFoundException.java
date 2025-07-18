package com.libros.librosrestapi.Libro.exception;

import lombok.Getter;

@Getter
public class LibroNotFoundException extends LibroException {

    public LibroNotFoundException(String code, String message) {
        super(code, message);
    }
}