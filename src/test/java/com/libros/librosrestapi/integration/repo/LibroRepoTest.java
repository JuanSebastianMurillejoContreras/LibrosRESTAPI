package com.libros.librosrestapi.integration.repo;

import com.libros.librosrestapi.libro.entity.LibroEntity;
import com.libros.librosrestapi.libro.repository.LibroRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibroRepoTest {

    @Autowired
    private LibroRepo libroRepo;

    @Test
    void testExistsByIsbn() {
        // Arrange
        String isbn = "1234567890";
        LibroEntity libro = new LibroEntity(null, "Título de prueba", "Autor de prueba", isbn);

        // Act
        libroRepo.save(libro);

        // Assert
        boolean exists = libroRepo.existsByIsbn(isbn);
        assertTrue(exists, "El libro debería existir por ISBN");

        boolean notExists = libroRepo.existsByIsbn("no-existe");
        assertFalse(notExists, "No debería existir un libro con ISBN inexistente");
    }

    @Test
    void testSaveAndFindById() {
        LibroEntity libro = new LibroEntity(null, "Otro título", "Otro autor", "0987654321");
        LibroEntity saved = libroRepo.save(libro);

        assertNotNull(saved.getId(), "El ID debería generarse después de guardar");

        LibroEntity found = libroRepo.findById(saved.getId()).orElse(null);

        assertNotNull(found, "El libro debería encontrarse por ID");
        assertEquals("Otro título", found.getTitulo());
        assertEquals("Otro autor", found.getAutor());
        assertEquals("0987654321", found.getIsbn());
    }

    @Test
    void testDelete() {
        LibroEntity libro = new LibroEntity(null, "Eliminar título", "Autor", "111222333");
        libro = libroRepo.save(libro);

        Long id = libro.getId();
        assertNotNull(id);

        libroRepo.deleteById(id);

        assertFalse(libroRepo.findById(id).isPresent(), "El libro debería eliminarse");
    }
}
