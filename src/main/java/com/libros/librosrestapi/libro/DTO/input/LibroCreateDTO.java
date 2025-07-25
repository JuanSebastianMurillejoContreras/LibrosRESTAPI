package com.libros.librosrestapi.libro.DTO.input;

public record LibroCreateDTO (
        String titulo,
        String autor,
        String isbn
) {}
