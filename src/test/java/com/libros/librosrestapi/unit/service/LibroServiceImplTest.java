package com.libros.librosrestapi.unit.service;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.exception.LibroException;
import com.libros.librosrestapi.Libro.exception.LibroNotFoundException;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import com.libros.librosrestapi.Libro.repository.LibroRepo;
import com.libros.librosrestapi.Libro.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepo libroRepo;

    @Mock
    private ILibroMapper libroMapper;

    @InjectMocks
    private LibroServiceImpl libroService;

    // Opcional: configurar mensajes de error si los usas como @Value
    @BeforeEach
    void setup() {
        libroService = new LibroServiceImpl(libroRepo, libroMapper);
        libroService.libroDoesntExistError = "El libro no existe";
        libroService.libroIsbnExistError = "El ISBN ya existe";
    }

    @Test
    void getLibros_devuelveListaDeLibros() {
        // Arrange
        LibroEntity entity = new LibroEntity(1, "Titulo", "Autor", "isbn-123");
        LibroResponseDTO dto = new LibroResponseDTO(1, "Titulo", "Autor", "isbn-123");

        when(libroRepo.findAll()).thenReturn(List.of(entity));
        when(libroMapper.libroEntityToLibroDTO(entity)).thenReturn(dto);

        // Act
        List<LibroResponseDTO> libros = libroService.getLibros();

        // Assert
        assertEquals(1, libros.size());
        assertEquals("Titulo", libros.get(0).getTitulo());
        verify(libroRepo, times(1)).findAll();
    }

    @Test
    void getLibro_existente_devuelveLibro() {
        // Arrange
        LibroEntity entity = new LibroEntity(1, "Titulo", "Autor", "isbn-123");
        LibroResponseDTO dto = new LibroResponseDTO(1, "Titulo", "Autor", "isbn-123");

        when(libroRepo.findById(1)).thenReturn(Optional.of(entity));
        when(libroMapper.libroEntityToLibroDTO(entity)).thenReturn(dto);

        // Act
        LibroResponseDTO result = libroService.getLibro(1);

        // Assert
        assertEquals("Titulo", result.getTitulo());
        verify(libroRepo).findById(1);
    }

    @Test
    void getLibro_noExistente_lanzaExcepcion() {
        // Arrange
        when(libroRepo.findById(1)).thenReturn(Optional.empty());
        // Act
        LibroNotFoundException ex = assertThrows(LibroNotFoundException.class, () -> libroService.getLibro(1));
        // Assert
        assertTrue(ex.getMessage().contains("El libro no existe"));
    }

    @Test
    void addLibro_isbnDuplicado_lanzaExcepcion() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(0, "Titulo", "Autor", "isbn-123");

        when(libroRepo.existsByIsbn("isbn-123")).thenReturn(true);

        LibroException ex = assertThrows(LibroException.class, () -> libroService.addLibro(dto));
        assertTrue(ex.getMessage().contains("El ISBN ya existe"));
    }

    @Test
    void addLibro_nuevoLibro_guardaCorrectamente() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(0, "Titulo", "Autor", "isbn-123");
        LibroEntity entity = new LibroEntity(0, "Titulo", "Autor", "isbn-123");
        LibroEntity savedEntity = new LibroEntity(1, "Titulo", "Autor", "isbn-123");
        LibroCreateRequestDTO savedDto = new LibroCreateRequestDTO(1, "Titulo", "Autor", "isbn-123");

        when(libroRepo.existsByIsbn("isbn-123")).thenReturn(false);
        when(libroMapper.libroDTOToLibroEntity(dto)).thenReturn(entity);
        when(libroRepo.save(entity)).thenReturn(savedEntity);
        when(libroMapper.libroEntityToLibroRequestDTO(savedEntity)).thenReturn(savedDto);

        LibroCreateRequestDTO result = libroService.addLibro(dto);

        assertEquals(1, result.getId());
        assertEquals("Titulo", result.getTitulo());
        verify(libroRepo).save(entity);
    }
    @Test
    void updateLibro_existente_actualizaCorrectamente() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(1, "Titulo Actualizado", "Autor", "isbn-123");
        LibroEntity entity = new LibroEntity(1, "Titulo Actualizado", "Autor", "isbn-123");
        LibroEntity savedEntity = new LibroEntity(1, "Titulo Actualizado", "Autor", "isbn-123");
        LibroCreateRequestDTO savedDto = new LibroCreateRequestDTO(1, "Titulo Actualizado", "Autor", "isbn-123");

        when(libroRepo.existsById(1)).thenReturn(true);
        when(libroMapper.libroDTOToLibroEntity(dto)).thenReturn(entity);
        when(libroRepo.save(entity)).thenReturn(savedEntity);
        when(libroMapper.libroEntityToLibroRequestDTO(savedEntity)).thenReturn(savedDto);

        LibroCreateRequestDTO result = libroService.updateLibro(dto);

        assertEquals(1, result.getId());
        assertEquals("Titulo Actualizado", result.getTitulo());
        verify(libroRepo).save(entity);
    }

    @Test
    void updateLibro_noExistente_lanzaExcepcion() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(99, "Titulo", "Autor", "isbn-123");

        when(libroRepo.existsById(99)).thenReturn(false);

        LibroNotFoundException ex = assertThrows(LibroNotFoundException.class, () -> libroService.updateLibro(dto));
        assertTrue(ex.getMessage().contains("El libro no existe"));
    }

    @Test
    void deleteLibro_existente_eliminaCorrectamente() {
        LibroEntity entity = new LibroEntity(1, "Titulo", "Autor", "isbn-123");

        when(libroRepo.findById(1)).thenReturn(Optional.of(entity));

        libroService.deleteLibro(1);

        verify(libroRepo).delete(entity);
    }

    @Test
    void deleteLibro_noExistente_lanzaExcepcion() {
        when(libroRepo.findById(99)).thenReturn(Optional.empty());

        LibroNotFoundException ex = assertThrows(LibroNotFoundException.class, () -> libroService.deleteLibro(99));
        assertTrue(ex.getMessage().contains("El libro no existe"));
    }

}
