package com.libros.librosrestapi.unit.dto;

import com.libros.librosrestapi.libro.DTO.input.LibroDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibroDTOTest {

    @Test
    void testLibroDTOFields() {
        // Arrange
        String titulo = "Cien años de soledad";
        String autor = "Gabriel García Márquez";
        String isbn = "978-3-16-148410-0";

        // Act
        LibroDTO libroDTO = new LibroDTO(titulo, autor, isbn);

        // Assert
        assertEquals(titulo, libroDTO.titulo());
        assertEquals(autor, libroDTO.autor());
        assertEquals(isbn, libroDTO.isbn());
    }

    @Test
    void testLibroDTOEquality() {
        LibroDTO libro1 = new LibroDTO("Titulo", "Autor", "ISBN");
        LibroDTO libro2 = new LibroDTO("Titulo", "Autor", "ISBN");

        assertEquals(libro1, libro2);
        assertEquals(libro1.hashCode(), libro2.hashCode());
    }

    @Test
    void testLibroDTOToString() {
        LibroDTO libro = new LibroDTO("Titulo", "Autor", "ISBN");
        String toString = libro.toString();

        assertTrue(toString.contains("Titulo"));
        assertTrue(toString.contains("Autor"));
        assertTrue(toString.contains("ISBN"));
    }
}
