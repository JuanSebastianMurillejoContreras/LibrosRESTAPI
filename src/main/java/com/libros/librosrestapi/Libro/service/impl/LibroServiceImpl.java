package com.libros.librosrestapi.Libro.service.impl;

import com.libros.librosrestapi.Libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateDTO;
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
    public String libroDoesntExistError;


    @Value("${properties.messages.error.isbn-exist}")
    public String libroIsbnExistError;


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
                        String.valueOf(HttpStatus.NOT_FOUND.value()),
                        libroDoesntExistError));
    }

    @Override
    public LibroDTO addLibro(LibroDTO libroDTO) {

        if (libroRepo.existsByIsbn(libroDTO.getIsbn()))
            throw new LibroException(String.valueOf(
                    HttpStatus.CONFLICT.value()),
                    libroIsbnExistError + ": " + libroDTO.getIsbn());

        LibroEntity libroEntity = libroMapper.libroDTOToLibroEntity(libroDTO);
        LibroEntity libroEntitySave = libroRepo.save(libroEntity);

        return libroMapper.libroEntityToLibroDTO(libroEntitySave);
    }

    @Override
    public LibroUpdateDTO updateLibro(Long id, LibroUpdateDTO libroUpdateDTO) {

        if (!libroRepo.existsById(id))
            throw new LibroNotFoundException(String.valueOf(
                    HttpStatus.NOT_FOUND.value()),
                    libroDoesntExistError + ": " + id);

        LibroEntity libroEntity = libroMapper.libroUpdateDTOToLibroEntity(libroUpdateDTO);
        LibroEntity libroEntitySaved = libroRepo.save(libroEntity);

        return libroMapper.libroEntityToLibroUpdateDTO(libroEntitySaved);
    }

    @Override
    public void deleteLibro(Long id) {
        LibroEntity libroEntity = libroRepo.findById(id)
                        .orElseThrow(()-> new LibroNotFoundException(
                                String.valueOf(HttpStatus.NOT_FOUND.value()),
                                libroDoesntExistError + ": " + id));
        libroRepo.delete(libroEntity);
    }

}
