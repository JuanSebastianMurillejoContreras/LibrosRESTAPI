package com.libros.librosrestapi.libro.mapper;

import com.libros.librosrestapi.libro.DTO.input.*;
import com.libros.librosrestapi.libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.libro.entity.LibroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ILibroMapper {

    // Entity → DTO
    LibroDTO libroEntityToLibroDTO(LibroEntity libroEntity);
    LibroUpdateDTO libroEntityToLibroUpdateDTO(LibroEntity libroEntity);
    LibroResponseDTO libroEntityToLibroResponseDTO(LibroEntity libroEntity);

    // DTO → Entity
    LibroEntity libroCreateDTOToLibroEntity(LibroCreateDTO libroCreateDTO);
    LibroEntity libroResponseDTOToLibroEntity(LibroResponseDTO libroResponseDTO);

    // DTO → DTO
    LibroUpdateDTO libroUpdateRequestDTOToLibroUpdateDTO(LibroUpdateRequestDTO libroUpdateRequestDTO);
    LibroCreateDTO libroCreateRequestDTOToLibroCreateDTO (LibroRequestDTO libroRequestDTO);
    LibroResponseDTO libroDTOToLibroResponseDTO(LibroDTO libroDTO);

    // Listas
    List<LibroResponseDTO> listLibroDTOToLibroResponseDTO(List<LibroDTO> libroDTO);

    // Métodos especiales
    void updateLibroEntityFromDTO(LibroUpdateDTO dto, @MappingTarget LibroEntity entity);

}
