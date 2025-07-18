package com.libros.librosrestapi.Libro.mapper;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateRequestDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroUpdateResponseDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ILibroMapper {

    LibroResponseDTO libroEntityToLibroResponseDTO(LibroEntity libroEntity);

    LibroCreateRequestDTO libroEntityToLibroRequestDTO(LibroEntity libroEntity);

    LibroEntity lbroDTOtoLibroEntity(LibroResponseDTO libroResponseDTO);

    LibroEntity libroDTOToLibroEntity(LibroDTO libroDTO);

    LibroDTO LibroCreateRequestDTOtoLibroDTO(LibroCreateRequestDTO libroCreateRequestDTO);

    LibroResponseDTO libroDTOToLibroResponseDTO(LibroDTO libroDTO);

    List<LibroResponseDTO> listLibroDTOToLibroResponseDTO(List<LibroDTO> libroDTO);

    LibroDTO libroEntityToLibroDTO(LibroEntity libroEntity);

    LibroEntity libroUpdateDTOToLibroEntity(LibroUpdateDTO libroUpdateDTO);

    LibroUpdateDTO libroUpdateRequestDTOToLibroUpdateDTO(LibroUpdateRequestDTO libroUpdateRequestDTO);

    LibroUpdateResponseDTO libroDTOToLibroUpdateResponseDTO(LibroDTO libroDTO);

    LibroUpdateDTO libroEntityToLibroUpdateDTO(LibroEntity libroEntity);

    LibroUpdateResponseDTO LibroUpdateResponseDTOToLibroUpdateResponseDTO (LibroUpdateDTO libroUpdateDTO);

}
