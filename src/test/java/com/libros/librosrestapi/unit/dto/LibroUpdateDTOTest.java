package com.libros.librosrestapi.unit.dto;

import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroUpdateDTOTest {

    @Test
    void testLibroUpdateDTOFields() {
        // Arrange
        String titulo = "Nuevo título";

        // Act
        LibroUpdateDTO libroUpdateDTO = new LibroUpdateDTO(titulo);

        // Assert
        assertEquals(titulo, libroUpdateDTO.titulo());
    }

    @Test
    void testLibroUpdateDTOEquality() {
        LibroUpdateDTO dto1 = new LibroUpdateDTO("Título");
        LibroUpdateDTO dto2 = new LibroUpdateDTO("Título");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testLibroUpdateDTOToString() {
        LibroUpdateDTO dto = new LibroUpdateDTO("Título");
        String toString = dto.toString();

        assertTrue(toString.contains("Título"));
    }
}