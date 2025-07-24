package com.libros.librosrestapi.Libro.service;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateDTO;


import java.util.List;

public interface LibroService {
    public List<LibroDTO> getLibros();
    public LibroDTO getLibro(Long id);
    public LibroCreateDTO addLibro(LibroCreateDTO libroCreateDTO);
    public LibroUpdateDTO updateLibro(Long id, LibroUpdateDTO libroUpdateDTO);
    public void deleteLibro(Long id);
}
