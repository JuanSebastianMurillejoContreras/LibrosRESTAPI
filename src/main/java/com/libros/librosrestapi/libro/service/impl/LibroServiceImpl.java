package com.libros.librosrestapi.libro.service.impl;

import com.libros.librosrestapi.libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.libro.constants.ErrorMessages;
import com.libros.librosrestapi.libro.entity.LibroEntity;
import com.libros.librosrestapi.libro.exception.LibroException;
import com.libros.librosrestapi.libro.exception.LibroNotFoundException;
import com.libros.librosrestapi.libro.mapper.ILibroMapper;
import com.libros.librosrestapi.libro.repository.LibroRepo;
import com.libros.librosrestapi.libro.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepo libroRepo;
    private final ILibroMapper libroMapper;


    @Override
    public List<LibroDTO> getLibros() {
        List<LibroEntity> libroEntityList = libroRepo.findAll();
        return libroEntityList.stream()
                .map(libroMapper::libroEntityToLibroDTO).toList();
    }

    @Override
    public LibroDTO getLibro(Long id) {
        return libroRepo.findById(id)
                .map(libroMapper::libroEntityToLibroDTO)
                .orElseThrow( () -> new LibroNotFoundException(
                        ErrorMessages.LIBRO_DOES_NOT_EXIST));
    }

    @Override
    public LibroResponseDTO addLibro(LibroCreateDTO libroCreateDTO) {

        LibroEntity libroEntity = libroMapper.libroCreateDTOToLibroEntity(libroCreateDTO);
        LibroEntity libroEntitySave = libroRepo.save(libroEntity);

        return libroMapper.libroEntityToLibroResponseDTO(libroEntitySave);
    }

    @Override
    public LibroUpdateDTO updateLibro(Long id, LibroUpdateDTO libroUpdateDTO) {

        LibroEntity existingLibro = libroRepo.findById(id)
                .orElseThrow(() -> new LibroNotFoundException(
                        ErrorMessages.LIBRO_DOES_NOT_EXIST + ": " + id));

        // Actualiza solo los campos permitidos desde el DTO usando el mapper
        libroMapper.updateLibroEntityFromDTO(libroUpdateDTO, existingLibro);

        // Guarda los cambios
        LibroEntity updatedLibro = libroRepo.save(existingLibro);

        return libroMapper.libroEntityToLibroUpdateDTO(updatedLibro);
    }


    @Override
    public void deleteLibro(Long id) {
        LibroEntity libroEntity = libroRepo.findById(id)
                        .orElseThrow(()-> new LibroNotFoundException(
                                ErrorMessages.LIBRO_DOES_NOT_EXIST + ": " + id));
        libroRepo.delete(libroEntity);
    }

}
