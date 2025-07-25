package com.libros.librosrestapi.integration.mapper;

import com.libros.librosrestapi.libro.DTO.input.LibroCreateRequestDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateRequestDTO;
import com.libros.librosrestapi.libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.libro.DTO.output.LibroUpdateResponseDTO;
import com.libros.librosrestapi.libro.entity.LibroEntity;
import com.libros.librosrestapi.libro.mapper.ILibroMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ILibroMapperIntegrationTest {

    @Autowired
    private ILibroMapper libroMapper;

    @Test
    void testEntityToDTO() {
        LibroEntity entity = new LibroEntity(1L, "El Quijote", "Cervantes", "1234567890123");
        LibroDTO dto = libroMapper.libroEntityToLibroDTO(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.titulo()).isEqualTo("El Quijote");
        assertThat(dto.autor()).isEqualTo("Cervantes");
        assertThat(dto.isbn()).isEqualTo("1234567890123");
    }

    @Test
    void testDTOToEntity() {
        LibroDTO dto = new LibroDTO("1984", "Orwell", "9876543210987");
        LibroEntity entity = libroMapper.libroDTOToLibroEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getTitulo()).isEqualTo("1984");
        assertThat(entity.getAutor()).isEqualTo("Orwell");
        assertThat(entity.getIsbn()).isEqualTo("9876543210987");
    }

    @Test
    void testCreateRequestDTOtoLibroDTO() {
        LibroCreateRequestDTO requestDTO = new LibroCreateRequestDTO("Fahrenheit 451", "Bradbury", "1111111111111", "Editorial X");
        LibroDTO dto = libroMapper.LibroCreateRequestDTOtoLibroDTO(requestDTO);

        assertThat(dto).isNotNull();
        assertThat(dto.titulo()).isEqualTo("Fahrenheit 451");
        assertThat(dto.autor()).isEqualTo("Bradbury");
        assertThat(dto.isbn()).isEqualTo("1111111111111");
    }

    @Test
    void testLibroUpdateRequestDTOToLibroUpdateDTO() {
        LibroUpdateRequestDTO updateRequestDTO = new LibroUpdateRequestDTO("Nuevo Título");
        LibroUpdateDTO updateDTO = libroMapper.libroUpdateRequestDTOToLibroUpdateDTO(updateRequestDTO);

        assertThat(updateDTO).isNotNull();
        assertThat(updateDTO.titulo()).isEqualTo("Nuevo Título");
    }

    @Test
    void testLibroDTOToLibroResponseDTO() {
        LibroDTO dto = new LibroDTO("Drácula", "Stoker", "2222222222222");
        LibroResponseDTO responseDTO = libroMapper.libroDTOToLibroResponseDTO(dto);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.titulo()).isEqualTo("Drácula");
    }

    @Test
    void testLibroUpdateDTOToLibroUpdateResponseDTO() {
        LibroUpdateDTO updateDTO = new LibroUpdateDTO("Título Actualizado");
        LibroUpdateResponseDTO responseDTO = libroMapper.LibroUpdateResponseDTOToLibroUpdateResponseDTO(updateDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.titulo()).isEqualTo("Título Actualizado");
    }

    @Test
    void testListLibroDTOToLibroResponseDTO() {
        List<LibroDTO> dtoList = List.of(
                new LibroDTO("libro 1", "Autor 1", "3333333333333"),
                new LibroDTO("libro 2", "Autor 2", "4444444444444")
        );

        List<LibroResponseDTO> responseList = libroMapper.listLibroDTOToLibroResponseDTO(dtoList);

        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0).titulo()).isEqualTo("libro 1");
        assertThat(responseList.get(1).titulo()).isEqualTo("libro 2");
    }

    @Test
    void testUpdateLibroEntityFromDTO() {
        LibroEntity entity = new LibroEntity(2L, "Original", "Autor Original", "5555555555555");
        LibroUpdateDTO updateDTO = new LibroUpdateDTO("Nuevo Título");

        libroMapper.updateLibroEntityFromDTO(updateDTO, entity);

        assertThat(entity.getTitulo()).isEqualTo("Nuevo Título");
        assertThat(entity.getAutor()).isEqualTo("Autor Original"); // no se toca
        assertThat(entity.getIsbn()).isEqualTo("5555555555555");   // no se toca
    }
}