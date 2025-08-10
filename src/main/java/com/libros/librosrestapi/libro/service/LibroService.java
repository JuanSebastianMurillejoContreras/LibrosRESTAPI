package com.libros.librosrestapi.libro.service;

import com.libros.librosrestapi.libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateDTO;

import java.util.List;

public interface LibroService {
    List<LibroDTO> getLibros();
    LibroDTO getLibro(Long id);
    LibroDTO addLibro(LibroCreateDTO libroCreateDTO);
    LibroDTO updateLibro(Long id, LibroUpdateDTO libroUpdateDTO);
    void deleteLibro(Long id);
}
