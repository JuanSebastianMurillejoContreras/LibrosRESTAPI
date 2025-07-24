package com.libros.librosrestapi.Libro.DTO.input;

public record LibroCreateDTO (
        String titulo,
        String autor,
        String isbn
) {}
