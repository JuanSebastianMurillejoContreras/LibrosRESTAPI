package com.libros.librosrestapi.unit.controller;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateRequestDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroUpdateResponseDTO;
import com.libros.librosrestapi.Libro.controller.LibroController;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import com.libros.librosrestapi.Libro.service.LibroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LibroControllerTest {

    @Mock
    private LibroService libroService;

    @Mock
    private ILibroMapper libroMapper;

    @InjectMocks
    private LibroController libroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLibros() {
        // Arrange
        LibroDTO dto1 = new LibroDTO("Titulo1", "Autor1", "ISBN1");
        LibroDTO dto2 = new LibroDTO("Titulo2", "Autor2", "ISBN2");
        List<LibroDTO> libroDTOList = Arrays.asList(dto1, dto2);

        LibroResponseDTO response1 = new LibroResponseDTO("Titulo1");
        LibroResponseDTO response2 = new LibroResponseDTO("Titulo2");
        List<LibroResponseDTO> responseList = Arrays.asList(response1, response2);

        when(libroService.getLibros()).thenReturn(libroDTOList);
        when(libroMapper.listLibroDTOToLibroResponseDTO(libroDTOList)).thenReturn(responseList);

        // Act
        ResponseEntity<List<LibroResponseDTO>> response = libroController.getLibros();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(libroService).getLibros();
        verify(libroMapper).listLibroDTOToLibroResponseDTO(libroDTOList);
    }

    @Test
    void testGetLibroById() {
        // Arrange
        Long id = 1L;
        LibroDTO libroDTO = new LibroDTO("Titulo", "Autor", "ISBN");
        LibroResponseDTO responseDTO = new LibroResponseDTO("Titulo");

        when(libroService.getLibro(id)).thenReturn(libroDTO);
        when(libroMapper.libroDTOToLibroResponseDTO(libroDTO)).thenReturn(responseDTO);

        // Act
        ResponseEntity<LibroResponseDTO> response = libroController.getLibroById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(libroService).getLibro(id);
        verify(libroMapper).libroDTOToLibroResponseDTO(libroDTO);
    }

    @Test
    void testCreateLibro() {
        // Arrange
        LibroCreateRequestDTO requestDTO = new LibroCreateRequestDTO("Titulo", "Autor", "9781234567890", "Editorial");
        LibroDTO libroDTO = new LibroDTO("Titulo", "Autor", "9781234567890");
        LibroDTO addedLibro = new LibroDTO("Titulo", "Autor", "9781234567890");
        LibroResponseDTO responseDTO = new LibroResponseDTO("Titulo");

        when(libroMapper.LibroCreateRequestDTOtoLibroDTO(requestDTO)).thenReturn(libroDTO);
        when(libroService.addLibro(libroDTO)).thenReturn(addedLibro);
        when(libroMapper.libroDTOToLibroResponseDTO(addedLibro)).thenReturn(responseDTO);

        // Act
        ResponseEntity<LibroResponseDTO> response = libroController.createLibro(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(libroMapper).LibroCreateRequestDTOtoLibroDTO(requestDTO);
        verify(libroService).addLibro(libroDTO);
        verify(libroMapper).libroDTOToLibroResponseDTO(addedLibro);
    }

    @Test
    void testUpdateLibro() {
        // Arrange
        Long id = 1L;
        LibroUpdateRequestDTO requestDTO = new LibroUpdateRequestDTO("Nuevo Titulo");
        LibroUpdateDTO updateDTO = new LibroUpdateDTO("Nuevo Titulo");
        LibroUpdateDTO updatedLibro = new LibroUpdateDTO("Nuevo Titulo");
        LibroUpdateResponseDTO responseDTO = new LibroUpdateResponseDTO("Nuevo Titulo");

        when(libroMapper.libroUpdateRequestDTOToLibroUpdateDTO(requestDTO)).thenReturn(updateDTO);
        when(libroService.updateLibro(id, updateDTO)).thenReturn(updatedLibro);
        when(libroMapper.LibroUpdateResponseDTOToLibroUpdateResponseDTO(updatedLibro)).thenReturn(responseDTO);

        // Act
        ResponseEntity<LibroUpdateResponseDTO> response = libroController.updateLibro(id, requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(libroMapper).libroUpdateRequestDTOToLibroUpdateDTO(requestDTO);
        verify(libroService).updateLibro(id, updateDTO);
        verify(libroMapper).LibroUpdateResponseDTOToLibroUpdateResponseDTO(updatedLibro);
    }

    @Test
    void testDeleteLibro() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = libroController.deleteLibro(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(libroService).deleteLibro(id);
    }
}
