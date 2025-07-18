package com.libros.librosrestapi.Libro.DTO.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroUpdateRequestDTO {

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

}
