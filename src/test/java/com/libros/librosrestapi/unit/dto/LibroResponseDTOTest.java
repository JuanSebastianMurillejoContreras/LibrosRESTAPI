package com.libros.librosrestapi.unit.dto;

import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroResponseDTOTest {

    @Test
    void testTituloGetter() {
        String titulo = "El Principito";
        LibroResponseDTO dto = new LibroResponseDTO(titulo);

        assertEquals(titulo, dto.titulo());
    }

    @Test
    void testEqualsAndHashCode() {
        LibroResponseDTO dto1 = new LibroResponseDTO("Título");
        LibroResponseDTO dto2 = new LibroResponseDTO("Título");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        LibroResponseDTO dto = new LibroResponseDTO("Título");
        String toString = dto.toString();

        assertTrue(toString.contains("Título"));
    }
}
