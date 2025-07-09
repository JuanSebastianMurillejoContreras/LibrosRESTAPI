package com.libros.librosrestapi.Libro.service.impl;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.entity.LibroEntity;
import com.libros.librosrestapi.Libro.exception.LibroException;
import com.libros.librosrestapi.Libro.exception.LibroNotFoundException;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import com.libros.librosrestapi.Libro.repository.LibroRepo;
import com.libros.librosrestapi.Libro.service.LibroService;
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


    @Value("${properties.messages.error.libro-does-not-exist}")
    private String libroDoesntExistError;


    @Value("${properties.messages.error.isbn-exist}")
    private String libroIsbnExistError;


    @Override
    public List<LibroResponseDTO> getLibros() {
        List<LibroEntity> libroEntityList = libroRepo.findAll();
        return libroEntityList.stream()
                .map(libroMapper::libroEntityToLibroDTO).toList();
    }

    @Override
    public LibroResponseDTO getLibro(int id) {
        return libroRepo.findById(id)
                .map(libroMapper::libroEntityToLibroDTO)
                .orElseThrow( () -> new LibroNotFoundException(
                        String.valueOf(HttpStatus.NOT_FOUND.value()),
                        libroDoesntExistError));
    }

    @Override
    public LibroRequestDTO addLibro(LibroRequestDTO libroRequestDTO) {

        if (libroRepo.existsByIsbn(libroRequestDTO.getIsbn()))
            throw new LibroException(String.valueOf(
                    HttpStatus.CONFLICT.value()),
                    libroIsbnExistError + ": " + libroRequestDTO.getIsbn());

        LibroEntity libroEntity = libroMapper.libroDTOToLibroEntity(libroRequestDTO);
        LibroEntity libroEntitySave = libroRepo.save(libroEntity);

        return libroMapper.libroEntityToLibroRequestDTO(libroEntitySave);
    }

    @Override
    public LibroRequestDTO updateLibro(LibroRequestDTO libroRequestDTO) {

        int id = libroRequestDTO.getId();

        if (!libroRepo.existsById(id))
            throw new LibroNotFoundException(String.valueOf(
                    HttpStatus.NOT_FOUND.value()),
                    libroDoesntExistError + ": " + libroRequestDTO.getId());

        LibroEntity libroEntity = libroMapper.libroDTOToLibroEntity(libroRequestDTO);
        LibroEntity libroEntitySaved = libroRepo.save(libroEntity);

        return libroMapper.libroEntityToLibroRequestDTO(libroEntitySaved);
    }

    @Override
    public void deleteLibro(int id) {
        LibroEntity libroEntity = libroRepo.findById(id)
                        .orElseThrow(()-> new LibroNotFoundException(
                                String.valueOf(HttpStatus.NOT_FOUND.value()),
                                libroDoesntExistError + ": " + id));
        libroRepo.delete(libroEntity);
    }

}
