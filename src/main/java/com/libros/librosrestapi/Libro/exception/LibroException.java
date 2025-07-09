package com.libros.librosrestapi.Libro.exception;


import lombok.Getter;

@Getter
public class LibroException extends RuntimeException  {

    private String code;

    public LibroException(String code, String message) {
        super(message);
        this.code = code;
    }
}