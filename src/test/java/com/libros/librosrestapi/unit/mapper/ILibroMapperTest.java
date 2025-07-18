package com.libros.librosrestapi.unit.mapper;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitario del mapper sin usar Spring.
 * Usamos Mappers.getMapper para instanciar directamente la implementación generada por MapStruct.
 * Esto permite ejecutar rápido y aislar solo la lógica de conversión.
 */

class ILibroMapperTest {

    private final ILibroMapper mapper = Mappers.getMapper(ILibroMapper.class);

    @Test
    void libroEntityToLibroDTO() {
        LibroEntity entity = new LibroEntity(1, "Titulo", "Autor", "isbn-123");

        LibroResponseDTO dto = mapper.libroEntityToLibroDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitulo(), dto.getTitulo());
        assertEquals(entity.getAutor(), dto.getAutor());
        assertEquals(entity.getIsbn(), dto.getIsbn());
    }

    @Test
    void libroEntityToLibroRequestDTO() {
        LibroEntity entity = new LibroEntity(2, "Titulo2", "Autor2", "isbn-456");

        LibroCreateRequestDTO dto = mapper.libroEntityToLibroRequestDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitulo(), dto.getTitulo());
        assertEquals(entity.getAutor(), dto.getAutor());
        assertEquals(entity.getIsbn(), dto.getIsbn());
    }

    @Test
    void libroDTOToLibroEntity_fromLibroRequestDTO() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(3, "Titulo3", "Autor3", "isbn-789");

        LibroEntity entity = mapper.libroDTOToLibroEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitulo(), entity.getTitulo());
        assertEquals(dto.getAutor(), entity.getAutor());
        assertEquals(dto.getIsbn(), entity.getIsbn());
    }

    @Test
    void libroDTOtoLibroEntity_fromLibroResponseDTO() {
        LibroResponseDTO dto = new LibroResponseDTO(4, "Titulo4", "Autor4", "isbn-000");

        LibroEntity entity = mapper.lbroDTOtoLibroEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitulo(), entity.getTitulo());
        assertEquals(dto.getAutor(), entity.getAutor());
        assertEquals(dto.getIsbn(), entity.getIsbn());
    }
}

