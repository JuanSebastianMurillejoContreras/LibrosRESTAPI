package com.libros.librosrestapi.Libro.DTO.output;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroResponseDTO {

    private int id;
    private String titulo;
    private String autor;
    private String isbn;

}