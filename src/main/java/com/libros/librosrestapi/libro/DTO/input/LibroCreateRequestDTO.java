package com.libros.librosrestapi.libro.DTO.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LibroCreateRequestDTO (

    @NotBlank(message = "El título no puede estar vacío")
    String titulo,

    @NotBlank(message = "El autor no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$",
            message = "El autor solo puede contener letras y espacios")
    String autor,

    @NotBlank(message = "El isbn no puede estar vacío")
    @Pattern(regexp = "^(\\d{13}|\\d{3}-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d{1})$",
            message = "El ISBN debe tener 13 dígitos o estar en formato con guiones como 978-3-16-148410-0")
    String isbn,

    String editorial
){}
