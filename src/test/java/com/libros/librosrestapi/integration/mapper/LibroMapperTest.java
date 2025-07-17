package com.libros.librosrestapi.integration.mapper;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test de integración para asegurar que ILibroMapper
 * está correctamente registrado como bean en Spring.
 * Usamos @SpringBootTest para arrancar el contexto de Spring
 * y verificar que se inyecta sin problemas.
 */

@SpringBootTest
class LibroMapperIntegrationTest {

    @Autowired
    private ILibroMapper mapper;

    @Test
    void testLibroEntityToLibroResponseDTO() {
        var entity = new LibroEntity(1, "Titulo", "Autor", "isbn-123");
        var dto = mapper.libroEntityToLibroDTO(entity);
        assertEquals("Titulo", dto.getTitulo());
        assertEquals("Autor", dto.getAutor());
        assertEquals("isbn-123", dto.getIsbn());
    }

    @Test
    void testLibroRequestDTOToLibroEntity() {
        var dto = new LibroRequestDTO(1, "Titulo", "Autor", "isbn-123");
        var entity = mapper.libroDTOToLibroEntity(dto);
        assertEquals("Titulo", entity.getTitulo());
        assertEquals("Autor", entity.getAutor());
        assertEquals("isbn-123", entity.getIsbn());
    }
}
