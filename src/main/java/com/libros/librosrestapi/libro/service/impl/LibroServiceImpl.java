package com.libros.librosrestapi.libro.service.impl;

import com.libros.librosrestapi.libro.DTO.input.LibroCreateDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateDTO;
import com.libros.librosrestapi.libro.constants.LibroErrorMessages;
import com.libros.librosrestapi.libro.entity.LibroEntity;
import com.libros.librosrestapi.libro.exception.LibroException;
import com.libros.librosrestapi.libro.exception.LibroNotFoundException;
import com.libros.librosrestapi.libro.mapper.ILibroMapper;
import com.libros.librosrestapi.libro.repository.LibroRepo;
import com.libros.librosrestapi.libro.service.LibroService;
import lombok.RequiredArgsConstructor;
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
        LibroEntity libroEntity = libroRepo.findById(id)
                .orElseThrow(()-> new LibroNotFoundException(LibroErrorMessages.LIBRO_DOES_NOT_EXIST));
        return  libroMapper.libroEntityToLibroDTO(libroEntity);
    }

    @Override
    public LibroDTO addLibro(LibroCreateDTO libroCreateDTO) {

        if (libroRepo.existsByIsbn(libroCreateDTO.isbn())) {
            throw new LibroException(LibroErrorMessages.ISBN_EXIST);
        }

        LibroEntity libroEntity = libroMapper.libroCreateDTOToLibroEntity(libroCreateDTO);
        LibroEntity libroEntitySave = libroRepo.save(libroEntity);

        return libroMapper.libroEntityToLibroDTO(libroEntitySave);
    }

    @Override
    public LibroDTO updateLibro(Long id, LibroUpdateDTO libroUpdateDTO) {

        LibroEntity existingLibro = libroRepo.findById(id)
                .orElseThrow(() -> new LibroNotFoundException(
                        LibroErrorMessages.LIBRO_DOES_NOT_EXIST + ": " + id));

        libroMapper.updateLibroEntityFromDTO(libroUpdateDTO, existingLibro);
        LibroEntity updatedLibro = libroRepo.save(existingLibro);

        return libroMapper.libroEntityToLibroDTO(updatedLibro);
    }


    @Override
    public void deleteLibro(Long id) {
        libroRepo.deleteById(id);
    }

}
