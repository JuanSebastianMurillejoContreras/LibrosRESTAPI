package com.libros.librosrestapi.Libro.controller;

import com.libros.librosrestapi.Libro.DTO.input.*;
import com.libros.librosrestapi.Libro.DTO.output.LibroUpdateResponseDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.mapper.ILibroMapper;
import com.libros.librosrestapi.Libro.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private final ILibroMapper libroMapper;

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> getLibros() {
        List<LibroDTO> libros = libroService.getLibros();
        List<LibroResponseDTO> libroResponseDTO = libroMapper.listLibroDTOToLibroResponseDTO(libros);

        return ResponseEntity.ok(libroResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> getLibroById(@PathVariable("id") Long id) {
        LibroDTO libroDTO = libroService.getLibro(id);
        LibroResponseDTO libro = libroMapper.libroDTOToLibroResponseDTO(libroDTO);

        return ResponseEntity.ok(libro);
    }

    @PostMapping()
    public ResponseEntity<LibroResponseDTO> createLibro(@RequestBody @Valid LibroCreateRequestDTO libroCreateRequestDTO) {

        LibroCreateDTO libroCreateDTO = libroMapper.libroCreateRequestDTOToLibroCreateDTO(libroCreateRequestDTO);
        LibroCreateDTO addLibro = libroService.addLibro(libroCreateDTO);
        LibroResponseDTO libroResponseDTO = libroMapper.LibroCreateDTOToLibroResponseDTO(addLibro);

        return ResponseEntity.status(HttpStatus.CREATED).body(libroResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroUpdateResponseDTO> updateLibro(@PathVariable Long id,
                                                       @RequestBody @Valid LibroUpdateRequestDTO libroUpdateRequestDTO) {

        LibroUpdateDTO libroUpdateDTO = libroMapper.libroUpdateRequestDTOToLibroUpdateDTO(libroUpdateRequestDTO);
        LibroUpdateDTO updateLibro = libroService.updateLibro(id, libroUpdateDTO);
        LibroUpdateResponseDTO libroUpdateResponseDTO = libroMapper.LibroUpdateResponseDTOToLibroUpdateResponseDTO(updateLibro);

        return ResponseEntity.ok(libroUpdateResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {

        libroService.deleteLibro(id);

        return ResponseEntity.noContent().build();
    }

}
