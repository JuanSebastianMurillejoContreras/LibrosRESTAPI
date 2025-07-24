package com.libros.librosrestapi.unit.service;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.exception.LibroException;
import com.libros.librosrestapi.Libro.exception.LibroNotFoundException;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import com.libros.librosrestapi.Libro.repository.LibroRepo;
import com.libros.librosrestapi.Libro.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibroServiceImplTest {

    @InjectMocks
    private LibroServiceImpl libroService;

    @Mock
    private LibroRepo libroRepo;

    @Mock
    private ILibroMapper libroMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        libroService.libroDoesntExistError = "El libro no existe";
        libroService.libroIsbnExistError = "El ISBN ya existe";
    }

    @Test
    void testGetLibros() {
        LibroEntity entity1 = new LibroEntity(1L, "Titulo1", "Autor1", "ISBN1");
        LibroEntity entity2 = new LibroEntity(2L, "Titulo2", "Autor2", "ISBN2");

        LibroDTO dto1 = new LibroDTO("Titulo1", "Autor1", "ISBN1");
        LibroDTO dto2 = new LibroDTO("Titulo2", "Autor2", "ISBN2");

        when(libroRepo.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        when(libroMapper.libroEntityToLibroDTO(entity1)).thenReturn(dto1);
        when(libroMapper.libroEntityToLibroDTO(entity2)).thenReturn(dto2);

        List<LibroDTO> result = libroService.getLibros();

        assertEquals(2, result.size());
        assertEquals("Titulo1", result.get(0).titulo());
        assertEquals("Titulo2", result.get(1).titulo());
    }

    @Test
    void testGetLibroFound() {
        LibroEntity entity = new LibroEntity(1L, "Titulo", "Autor", "ISBN");
        LibroDTO dto = new LibroDTO("Titulo", "Autor", "ISBN");

        when(libroRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(libroMapper.libroEntityToLibroDTO(entity)).thenReturn(dto);

        LibroDTO result = libroService.getLibro(1L);

        assertEquals("Titulo", result.titulo());
    }

    @Test
    void testGetLibroNotFound() {
        when(libroRepo.findById(99L)).thenReturn(Optional.empty());

        LibroNotFoundException ex = assertThrows(LibroNotFoundException.class,
                () -> libroService.getLibro(99L));

        assertEquals(String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getCode());
        assertTrue(ex.getMessage().contains("El libro no existe"));
    }

    @Test
    void testAddLibroSuccess() {
        LibroCreateDTO dto = new LibroCreateDTO("Titulo", "Autor", "ISBN");
        LibroEntity entity = new LibroEntity(null, "Titulo", "Autor", "ISBN");
        LibroEntity savedEntity = new LibroEntity(1L, "Titulo", "Autor", "ISBN");
        LibroCreateDTO savedDto = new LibroCreateDTO("Titulo", "Autor", "ISBN");

        when(libroRepo.existsByIsbn("ISBN")).thenReturn(false);
        when(libroMapper.libroCreateDTOToLibroEntity(dto)).thenReturn(entity);
        when(libroRepo.save(entity)).thenReturn(savedEntity);
        when(libroMapper.libroEntityToLibroCreateDTO(savedEntity)).thenReturn(savedDto);

        LibroCreateDTO result = libroService.addLibro(dto);

        assertEquals("Titulo", result.titulo());
        assertEquals("Autor", result.autor());
    }

    @Test
    void testAddLibroIsbnExists() {
        LibroCreateDTO dto = new LibroCreateDTO("Titulo", "Autor", "ISBN");
        when(libroRepo.existsByIsbn("ISBN")).thenReturn(true);

        LibroException ex = assertThrows(LibroException.class, () -> libroService.addLibro(dto));

        assertEquals(String.valueOf(HttpStatus.CONFLICT.value()), ex.getCode());
        assertTrue(ex.getMessage().contains("El ISBN ya existe"));
    }

    @Test
    void testUpdateLibroSuccess() {
        LibroEntity existingEntity = new LibroEntity(1L, "Old Title", "Autor", "ISBN");
        LibroUpdateDTO updateDTO = new LibroUpdateDTO("New Title");
        LibroEntity updatedEntity = new LibroEntity(1L, "New Title", "Autor", "ISBN");
        LibroUpdateDTO updatedDTO = new LibroUpdateDTO("New Title");

        when(libroRepo.findById(1L)).thenReturn(Optional.of(existingEntity));
        doAnswer(invocation -> {
            existingEntity.setTitulo(updateDTO.titulo());
            return null;
        }).when(libroMapper).updateLibroEntityFromDTO(updateDTO, existingEntity);
        when(libroRepo.save(existingEntity)).thenReturn(updatedEntity);
        when(libroMapper.libroEntityToLibroUpdateDTO(updatedEntity)).thenReturn(updatedDTO);

        LibroUpdateDTO result = libroService.updateLibro(1L, updateDTO);

        assertEquals("New Title", result.titulo());
    }

    @Test
    void testUpdateLibroNotFound() {
        when(libroRepo.findById(99L)).thenReturn(Optional.empty());
        LibroUpdateDTO updateDTO = new LibroUpdateDTO("Title");

        LibroNotFoundException ex = assertThrows(LibroNotFoundException.class,
                () -> libroService.updateLibro(99L, updateDTO));

        assertEquals(String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getCode());
        assertTrue(ex.getMessage().contains("El libro no existe"));
    }

    @Test
    void testDeleteLibroSuccess() {
        LibroEntity entity = new LibroEntity(1L, "Titulo", "Autor", "ISBN");
        when(libroRepo.findById(1L)).thenReturn(Optional.of(entity));

        libroService.deleteLibro(1L);

        verify(libroRepo, times(1)).delete(entity);
    }

    @Test
    void testDeleteLibroNotFound() {
        when(libroRepo.findById(99L)).thenReturn(Optional.empty());

        LibroNotFoundException ex = assertThrows(LibroNotFoundException.class,
                () -> libroService.deleteLibro(99L));

        assertEquals(String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getCode());
    }
}