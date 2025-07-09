package com.libros.librosrestapi.Libro.mapper;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILibroMapper {

    LibroResponseDTO libroEntityToLibroDTO(LibroEntity libroEntity);

    LibroRequestDTO libroEntityToLibroRequestDTO(LibroEntity libroEntity);

    LibroEntity lbroDTOtoLibroEntity(LibroResponseDTO libroResponseDTO);

    LibroEntity libroDTOToLibroEntity(LibroRequestDTO libroDetailDTO);

}
