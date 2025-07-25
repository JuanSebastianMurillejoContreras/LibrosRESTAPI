package com.libros.librosrestapi.libro.exception;

import lombok.Getter;

@Getter
public class LibroNotFoundException extends LibroException {

    public LibroNotFoundException(String message){
        super(message);
    }
}