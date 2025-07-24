package com.libros.librosrestapi.unit.dto;

import com.libros.librosrestapi.Libro.DTO.output.LibroUpdateResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroUpdateResponseDTOTest {

    @Test
    void testTituloGetter() {
        String titulo = "Título actualizado";
        LibroUpdateResponseDTO dto = new LibroUpdateResponseDTO(titulo);

        assertEquals(titulo, dto.titulo());
    }

    @Test
    void testEqualsAndHashCode() {
        LibroUpdateResponseDTO dto1 = new LibroUpdateResponseDTO("Nuevo Título");
        LibroUpdateResponseDTO dto2 = new LibroUpdateResponseDTO("Nuevo Título");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        LibroUpdateResponseDTO dto = new LibroUpdateResponseDTO("Nuevo Título");
        String toString = dto.toString();

        assertTrue(toString.contains("Nuevo Título"));
    }
}