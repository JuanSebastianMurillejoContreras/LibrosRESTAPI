package com.libros.librosrestapi;


import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.repository.LibroRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibroRepoTest {

    @Autowired
    private LibroRepo libroRepo;

    @Test
    void save_and_findById() {
        LibroEntity libro = new LibroEntity(0, "Titulo Test", "Autor Test", "isbn-1234567890123");
        LibroEntity saved = libroRepo.save(libro);

        Optional<LibroEntity> found = libroRepo.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Titulo Test", found.get().getTitulo());
    }

    @Test
    void existsByIsbn_retornaTrueSiExiste() {
        LibroEntity libro = new LibroEntity(0, "Otro Titulo", "Otro Autor", "isbn-0000000000000");
        libroRepo.save(libro);

        boolean exists = libroRepo.existsByIsbn("isbn-0000000000000");

        assertTrue(exists);
    }

    @Test
    void existsByIsbn_retornaFalseSiNoExiste() {
        boolean exists = libroRepo.existsByIsbn("isbn-no-existe");
        assertFalse(exists);
    }

    @Test
    void delete_remueveLibro() {
        LibroEntity libro = new LibroEntity(0, "Titulo Borrar", "Autor Borrar", "isbn-borrar");
        LibroEntity saved = libroRepo.save(libro);

        libroRepo.delete(saved);

        Optional<LibroEntity> found = libroRepo.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}
