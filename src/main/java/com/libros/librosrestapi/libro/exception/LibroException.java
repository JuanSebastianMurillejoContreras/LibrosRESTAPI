package com.libros.librosrestapi.libro.exception;


import lombok.Getter;

@Getter
public class LibroException extends RuntimeException  {

    public LibroException(String message) {
        super(message);

    }
}