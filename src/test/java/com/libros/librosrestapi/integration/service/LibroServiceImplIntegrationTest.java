package com.libros.librosrestapi.integration.service;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import com.libros.librosrestapi.Libro.repository.LibroRepo;
import com.libros.librosrestapi.Libro.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // opcional, si tienes application-test.yml
@AutoConfigureTestDatabase // fuerza a usar H2
class LibroServiceImplIntegrationTest {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroRepo libroRepo;

    @BeforeEach
    void setUp() {
        libroRepo.deleteAll();
    }

    @Test
    void testAddAndGetLibro() {
        // Given
        LibroCreateDTO newLibro = new LibroCreateDTO("El Principito", "Saint-Exupéry", "1234567890123");

        // When
        LibroCreateDTO saved = libroService.addLibro(newLibro);
        List<LibroDTO> allLibros = libroService.getLibros();

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.titulo()).isEqualTo("El Principito");
        assertThat(allLibros).hasSize(1);
        assertThat(allLibros.get(0).titulo()).isEqualTo("El Principito");
    }

    @Test
    void testUpdateLibro() {
        // Given
        LibroEntity entity = new LibroEntity(null, "Original", "Autor", "1111111111111");
        entity = libroRepo.save(entity);

        // When
        LibroUpdateDTO updated = libroService.updateLibro(entity.getId(), new LibroUpdateDTO("Nuevo Título"));

        // Then
        assertThat(updated).isNotNull();
        assertThat(updated.titulo()).isEqualTo("Nuevo Título");

        // Check in DB
        LibroEntity updatedEntity = libroRepo.findById(entity.getId()).orElseThrow();
        assertThat(updatedEntity.getTitulo()).isEqualTo("Nuevo Título");
    }

    @Test
    void testDeleteLibro() {
        // Given
        LibroEntity entity = new LibroEntity(null, "Borrar", "Autor", "2222222222222");
        entity = libroRepo.save(entity);

        // When
        libroService.deleteLibro(entity.getId());

        // Then
        boolean exists = libroRepo.existsById(entity.getId());
        assertThat(exists).isFalse();
    }
}