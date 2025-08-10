package com.libros.librosrestapi.unit.service;

import com.libros.librosrestapi.libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.libro.entity.LibroEntity;
import com.libros.librosrestapi.libro.exception.LibroException;
import com.libros.librosrestapi.libro.exception.LibroNotFoundException;
import com.libros.librosrestapi.libro.mapper.ILibroMapper;
import com.libros.librosrestapi.libro.repository.LibroRepo;
import com.libros.librosrestapi.libro.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibroServiceImplTest {

    @Mock
    private LibroRepo libroRepo;

    @Mock
    private ILibroMapper libroMapper;

    @InjectMocks
    private LibroServiceImpl libroService;

    private LibroEntity libroEntity;
    private LibroDTO libroDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        libroEntity = new LibroEntity();
        libroEntity.setId(1L);

        libroDTO = new LibroDTO("Título", "Autor", "1234567890");
    }

    @Test
    void getLibros_retornaListaDTO() {
        when(libroRepo.findAll()).thenReturn(Arrays.asList(libroEntity));
        when(libroMapper.libroEntityToLibroDTO(libroEntity)).thenReturn(libroDTO);

        List<LibroDTO> resultado = libroService.getLibros();

        assertEquals(1, resultado.size());
        assertEquals("Título", resultado.get(0).titulo());
        verify(libroRepo).findAll();
        verify(libroMapper).libroEntityToLibroDTO(libroEntity);
    }

    @Test
    void getLibro_existente_retornaDTO() {
        when(libroRepo.findById(1L)).thenReturn(Optional.of(libroEntity));
        when(libroMapper.libroEntityToLibroDTO(libroEntity)).thenReturn(libroDTO);

        LibroDTO resultado = libroService.getLibro(1L);

        assertNotNull(resultado);
        assertEquals("Título", resultado.titulo());
        verify(libroRepo).findById(1L);
        verify(libroMapper).libroEntityToLibroDTO(libroEntity);
    }

    @Test
    void getLibro_noExistente_lanzaExcepcion() {
        when(libroRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LibroNotFoundException.class, () -> libroService.getLibro(1L));
        verify(libroRepo).findById(1L);
    }

    @Test
    void addLibro_conIsbnExistente_lanzaExcepcion() {
        LibroCreateDTO createDTO = new LibroCreateDTO("Título", "Autor", "1234567890");
        when(libroRepo.existsByIsbn(createDTO.isbn())).thenReturn(true);

        assertThrows(LibroException.class, () -> libroService.addLibro(createDTO));
        verify(libroRepo).existsByIsbn(createDTO.isbn());
    }

    @Test
    void addLibro_valido_guardaYRetornaDTO() {
        LibroCreateDTO createDTO = new LibroCreateDTO("Título", "Autor", "1234567890");

        when(libroRepo.existsByIsbn(createDTO.isbn())).thenReturn(false);
        when(libroMapper.libroCreateDTOToLibroEntity(createDTO)).thenReturn(libroEntity);
        when(libroRepo.save(libroEntity)).thenReturn(libroEntity);
        when(libroMapper.libroEntityToLibroDTO(libroEntity)).thenReturn(libroDTO);

        LibroDTO resultado = libroService.addLibro(createDTO);

        assertEquals("Título", resultado.titulo());
        verify(libroRepo).save(libroEntity);
    }

    @Test
    void updateLibro_existente_actualizaYRetornaDTO() {
        LibroUpdateDTO updateDTO = new LibroUpdateDTO("Nuevo Título");

        when(libroRepo.findById(1L)).thenReturn(Optional.of(libroEntity));
        doNothing().when(libroMapper).updateLibroEntityFromDTO(updateDTO, libroEntity);
        when(libroRepo.save(libroEntity)).thenReturn(libroEntity);
        when(libroMapper.libroEntityToLibroDTO(libroEntity)).thenReturn(libroDTO);

        LibroDTO resultado = libroService.updateLibro(1L, updateDTO);

        assertEquals("Título", resultado.titulo());
        verify(libroMapper).updateLibroEntityFromDTO(updateDTO, libroEntity);
    }

    @Test
    void updateLibro_noExistente_lanzaExcepcion() {
        LibroUpdateDTO updateDTO = new LibroUpdateDTO("Nuevo Título");

        when(libroRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LibroNotFoundException.class, () -> libroService.updateLibro(1L, updateDTO));
        verify(libroRepo).findById(1L);
    }

    @Test
    void deleteLibro_invocaRepositorio() {
        doNothing().when(libroRepo).deleteById(1L);

        libroService.deleteLibro(1L);

        verify(libroRepo).deleteById(1L);
    }
}
