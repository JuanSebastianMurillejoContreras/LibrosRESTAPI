package com.libros.librosrestapi.libro.DTO.input;

import jakarta.validation.constraints.NotBlank;

public record LibroUpdateRequestDTO (
        @NotBlank(message = "El título no puede estar vacío")
        String titulo){}
