package com.libros.librosrestapi.unit.entity;

import com.libros.librosrestapi.Libro.entity.LibroEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroEntityTest {

    @Test
    void testConstructoresYGettersSetters() {
        LibroEntity libro = new LibroEntity();
        libro.setId(1);
        libro.setTitulo("El Principito");
        libro.setAutor("Antoine de Saint-Exupéry");
        libro.setIsbn("978-1234567890");

        assertEquals(1, libro.getId());
        assertEquals("El Principito", libro.getTitulo());
        assertEquals("Antoine de Saint-Exupéry", libro.getAutor());
        assertEquals("978-1234567890", libro.getIsbn());

        LibroEntity libro2 = new LibroEntity(2, "1984", "George Orwell", "978-0987654321");
        assertEquals(2, libro2.getId());
        assertEquals("1984", libro2.getTitulo());
        assertEquals("George Orwell", libro2.getAutor());
        assertEquals("978-0987654321", libro2.getIsbn());
    }

    @Test
    void testEqualsAndHashCode() {
        LibroEntity libro1 = new LibroEntity(1, "1984", "George Orwell", "978-0987654321");
        LibroEntity libro2 = new LibroEntity(1, "1984", "George Orwell", "978-0987654321");

        assertEquals(libro1, libro2);
        assertEquals(libro1.hashCode(), libro2.hashCode());
    }

    @Test
    void testToString() {
        LibroEntity libro = new LibroEntity(1, "1984", "George Orwell", "978-0987654321");
        String str = libro.toString();

        assertTrue(str.contains("1984"));
        assertTrue(str.contains("George Orwell"));
        assertTrue(str.contains("978-0987654321"));
    }
}