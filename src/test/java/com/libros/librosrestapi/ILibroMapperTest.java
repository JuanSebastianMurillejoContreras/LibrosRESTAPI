package com.libros.librosrestapi;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

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

        LibroRequestDTO dto = mapper.libroEntityToLibroRequestDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitulo(), dto.getTitulo());
        assertEquals(entity.getAutor(), dto.getAutor());
        assertEquals(entity.getIsbn(), dto.getIsbn());
    }

    @Test
    void libroDTOToLibroEntity_fromLibroRequestDTO() {
        LibroRequestDTO dto = new LibroRequestDTO(3, "Titulo3", "Autor3", "isbn-789");

        LibroEntity entity = mapper.libroDTOToLibroEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitulo(), entity.getTitulo());
        assertEquals(dto.getAutor(), entity.getAutor());
        assertEquals(dto.getIsbn(), entity.getIsbn());
    }

    @Test
    void lbroDTOtoLibroEntity_fromLibroResponseDTO() {
        LibroResponseDTO dto = new LibroResponseDTO(4, "Titulo4", "Autor4", "isbn-000");

        LibroEntity entity = mapper.lbroDTOtoLibroEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitulo(), entity.getTitulo());
        assertEquals(dto.getAutor(), entity.getAutor());
        assertEquals(dto.getIsbn(), entity.getIsbn());
    }
}

