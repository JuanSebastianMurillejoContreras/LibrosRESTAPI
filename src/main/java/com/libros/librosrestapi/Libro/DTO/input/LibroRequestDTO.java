package com.libros.librosrestapi.Libro.DTO.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LibroRequestDTO {

    private int id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$",
            message = "El autor solo puede contener letras y espacios")
    private String autor;

    @NotBlank(message = "El isbn no puede estar vacío")
    @Pattern(regexp = "^(\\d{13}|\\d{3}-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d{1})$",
            message = "El ISBN debe tener 13 dígitos o estar en formato con guiones como 978-3-16-148410-0")
    private String isbn;

}
