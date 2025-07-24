package com.libros.librosrestapi.Libro.mapper;

import com.libros.librosrestapi.Libro.DTO.input.*;
import com.libros.librosrestapi.Libro.DTO.output.LibroUpdateResponseDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ILibroMapper {

    // Entity → DTO
    LibroDTO libroEntityToLibroDTO(LibroEntity libroEntity);
    LibroUpdateDTO libroEntityToLibroUpdateDTO(LibroEntity libroEntity);
    LibroCreateDTO libroEntityToLibroCreateDTO(LibroEntity libroEntity);

    // DTO → Entity
    LibroEntity libroDTOToLibroEntity(LibroDTO libroDTO);
    LibroEntity libropCreateDTOToLibroEntity(LibroCreateDTO libroCreateDTO);

    // DTO → DTO
    LibroDTO LibroCreateRequestDTOtoLibroDTO(LibroCreateRequestDTO libroCreateRequestDTO);
    LibroUpdateDTO libroUpdateRequestDTOToLibroUpdateDTO(LibroUpdateRequestDTO libroUpdateRequestDTO);
    LibroCreateDTO libroCreateRequestDTOToLibroCreateDTO (LibroCreateRequestDTO libroCreateRequestDTO);
    LibroResponseDTO LibroCreateDTOToLibroResponseDTO(LibroCreateDTO libroCreateDTO);
    LibroResponseDTO libroDTOToLibroResponseDTO(LibroDTO libroDTO);
    LibroUpdateResponseDTO LibroUpdateResponseDTOToLibroUpdateResponseDTO (LibroUpdateDTO libroUpdateDTO);

    // Listas
    List<LibroResponseDTO> listLibroDTOToLibroResponseDTO(List<LibroDTO> libroDTO);

    // Métodos especiales
    void updateLibroEntityFromDTO(LibroUpdateDTO dto, @MappingTarget LibroEntity entity);

}
