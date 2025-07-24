package com.libros.librosrestapi.unit.entity;

import com.libros.librosrestapi.Libro.entity.LibroEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroEntityTest {

    @Test
    void testGettersAndSetters() {
        LibroEntity libro = new LibroEntity();

        libro.setId(1L);
        libro.setTitulo("1984");
        libro.setAutor("George Orwell");
        libro.setIsbn("1234567890");

        assertEquals(1L, libro.getId());
        assertEquals("1984", libro.getTitulo());
        assertEquals("George Orwell", libro.getAutor());
        assertEquals("1234567890", libro.getIsbn());
    }

    @Test
    void testAllArgsConstructor() {
        LibroEntity libro = new LibroEntity(2L, "Animal Farm", "George Orwell", "0987654321");

        assertEquals(2L, libro.getId());
        assertEquals("Animal Farm", libro.getTitulo());
        assertEquals("George Orwell", libro.getAutor());
        assertEquals("0987654321", libro.getIsbn());
    }

    @Test
    void testEqualsAndHashCode() {
        LibroEntity libro1 = new LibroEntity(3L, "Brave New World", "Aldous Huxley", "1122334455");
        LibroEntity libro2 = new LibroEntity(3L, "Brave New World", "Aldous Huxley", "1122334455");

        assertEquals(libro1, libro2);
        assertEquals(libro1.hashCode(), libro2.hashCode());
    }

    @Test
    void testNotEquals() {
        LibroEntity libro1 = new LibroEntity(4L, "Dune", "Frank Herbert", "2233445566");
        LibroEntity libro2 = new LibroEntity(5L, "Dune", "Frank Herbert", "2233445566");

        assertNotEquals(libro1, libro2); // tienen id diferente
    }

    @Test
    void testToString() {
        LibroEntity libro = new LibroEntity(6L, "Fahrenheit 451", "Ray Bradbury", "3344556677");

        String toString = libro.toString();

        assertTrue(toString.contains("Fahrenheit 451"));
        assertTrue(toString.contains("Ray Bradbury"));
        assertTrue(toString.contains("3344556677"));
        assertTrue(toString.contains("6"));
    }
}
