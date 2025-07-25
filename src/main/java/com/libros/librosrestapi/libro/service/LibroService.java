package com.libros.librosrestapi.libro.service;

import com.libros.librosrestapi.libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.libro.DTO.output.LibroResponseDTO;


import java.util.List;

public interface LibroService {
    public List<LibroDTO> getLibros();
    public LibroDTO getLibro(Long id);
    public LibroResponseDTO addLibro(LibroCreateDTO libroCreateDTO);
    public LibroUpdateDTO updateLibro(Long id, LibroUpdateDTO libroUpdateDTO);
    public void deleteLibro(Long id);
}
