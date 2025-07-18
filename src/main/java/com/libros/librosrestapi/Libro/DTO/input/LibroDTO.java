package com.libros.librosrestapi.Libro.DTO.input;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LibroDTO {

    private String titulo;
    private String autor;
    private String isbn;

}
