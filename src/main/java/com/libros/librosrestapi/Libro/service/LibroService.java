package com.libros.librosrestapi.Libro.service;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;

import java.util.List;

public interface LibroService {
    public List<LibroResponseDTO> getLibros();
    public LibroResponseDTO getLibro(int id);
    public LibroRequestDTO addLibro(LibroRequestDTO libroRequestDTO);
    public LibroRequestDTO updateLibro(LibroRequestDTO libroRequestDTO);
    public void deleteLibro(int id);
}
