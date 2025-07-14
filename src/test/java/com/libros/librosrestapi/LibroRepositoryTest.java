package com.libros.librosrestapi;

import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.repository.LibroRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibroRepositoryTest {

    @Autowired
    private LibroRepo libroRepository;

    @Test
    void guardarYRecuperarLibro() {
        LibroEntity libro = new LibroEntity();
        libro.setTitulo("Cien años de soledad");
        libro.setAutor("Gabriel García Márquez");
        libro.setIsbn("978-0060883287");

        libro = libroRepository.save(libro); // save devuelve el objeto con el ID asignado

        Optional<LibroEntity> recuperado = libroRepository.findById(libro.getId());
        assertTrue(recuperado.isPresent());
        assertEquals("Cien años de soledad", recuperado.get().getTitulo());
        assertEquals("Gabriel García Márquez", recuperado.get().getAutor());
        assertEquals("978-0060883287", recuperado.get().getIsbn());
    }

    @Test
    void noPermiteIsbnDuplicado() {
        LibroEntity libro1 = new LibroEntity(0, "Libro 1", "Autor 1", "isbn-123");
        libroRepository.save(libro1);

        LibroEntity libro2 = new LibroEntity(0, "Libro 2", "Autor 2", "isbn-123");

        assertThrows(DataIntegrityViolationException.class, () -> {
            libroRepository.saveAndFlush(libro2); // saveAndFlush obliga a insertar inmediatamente
        });
    }
}