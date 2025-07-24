package com.libros.librosrestapi.unit.dto;

import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroResponseDTOTest {

    @Test
    void testTituloGetter() {
        String titulo = "El Principito";
        String autor = "Autor";
        LibroResponseDTO dto = new LibroResponseDTO(titulo, autor);

        assertEquals(titulo, dto.titulo());
    }

    @Test
    void testEqualsAndHashCode() {
        LibroResponseDTO dto1 = new LibroResponseDTO("Título","Autor");
        LibroResponseDTO dto2 = new LibroResponseDTO("Título","Autor");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        LibroResponseDTO dto = new LibroResponseDTO("Título","Autor");
        String toString = dto.toString();

        assertTrue(toString.contains("Título"));
    }
}
