package com.libros.librosrestapi.unit.mapper;

import com.libros.librosrestapi.Libro.DTO.input.*;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroUpdateResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ILibroMapperTest {

    @Autowired
    private ILibroMapper mapper;

    @Test
    void testLibroEntityToLibroDTO() {
        LibroEntity entity = new LibroEntity(1L, "Titulo", "Autor", "ISBN");
        LibroDTO dto = mapper.libroEntityToLibroDTO(entity);

        assertEquals(entity.getTitulo(), dto.titulo());
        assertEquals(entity.getAutor(), dto.autor());
        assertEquals(entity.getIsbn(), dto.isbn());
    }

    @Test
    void testLibroDTOToLibroEntity() {
        LibroDTO dto = new LibroDTO("Titulo", "Autor", "ISBN");
        LibroEntity entity = mapper.libroDTOToLibroEntity(dto);

        assertEquals(dto.titulo(), entity.getTitulo());
        assertEquals(dto.autor(), entity.getAutor());
        assertEquals(dto.isbn(), entity.getIsbn());
    }

    @Test
    void testLibroEntityToLibroUpdateDTO() {
        LibroEntity entity = new LibroEntity(2L, "Nuevo titulo", "Autor", "ISBN");
        LibroUpdateDTO dto = mapper.libroEntityToLibroUpdateDTO(entity);

        assertEquals(entity.getTitulo(), dto.titulo());
    }

    @Test
    void testLibroCreateRequestDTOtoLibroDTO() {
        LibroCreateRequestDTO request = new LibroCreateRequestDTO("Titulo", "Autor", "ISBN", "Planeta");
        LibroDTO dto = mapper.LibroCreateRequestDTOtoLibroDTO(request);

        assertEquals(request.titulo(), dto.titulo());
        assertEquals(request.autor(), dto.autor());
        assertEquals(request.isbn(), dto.isbn());
    }

    @Test
    void testLibroUpdateRequestDTOToLibroUpdateDTO() {
        LibroUpdateRequestDTO request = new LibroUpdateRequestDTO("Nuevo titulo");
        LibroUpdateDTO dto = mapper.libroUpdateRequestDTOToLibroUpdateDTO(request);

        assertEquals(request.titulo(), dto.titulo());
    }

    @Test
    void testLibroDTOToLibroResponseDTO() {
        LibroDTO dto = new LibroDTO("Titulo", "Autor", "ISBN");
        LibroResponseDTO response = mapper.libroDTOToLibroResponseDTO(dto);

        assertEquals(dto.titulo(), response.titulo());
    }

    @Test
    void testLibroUpdateResponseDTOToLibroUpdateResponseDTO() {
        LibroUpdateDTO dto = new LibroUpdateDTO("Nuevo titulo");
        LibroUpdateResponseDTO response = mapper.LibroUpdateResponseDTOToLibroUpdateResponseDTO(dto);

        assertEquals(dto.titulo(), response.titulo());
    }

    @Test
    void testListLibroDTOToLibroResponseDTO() {
        List<LibroDTO> list = List.of(
                new LibroDTO("Titulo1", "Autor1", "ISBN1"),
                new LibroDTO("Titulo2", "Autor2", "ISBN2")
        );

        List<LibroResponseDTO> responseList = mapper.listLibroDTOToLibroResponseDTO(list);

        assertEquals(2, responseList.size());
        assertEquals("Titulo1", responseList.get(0).titulo());
        assertEquals("Titulo2", responseList.get(1).titulo());
    }

    @Test
    void testUpdateLibroEntityFromDTO() {
        LibroEntity entity = new LibroEntity(3L, "Antiguo titulo", "Autor", "ISBN");
        LibroUpdateDTO dto = new LibroUpdateDTO("Titulo actualizado");

        mapper.updateLibroEntityFromDTO(dto, entity);

        assertEquals("Titulo actualizado", entity.getTitulo());
        assertEquals("Autor", entity.getAutor());
        assertEquals("ISBN", entity.getIsbn());
    }
}