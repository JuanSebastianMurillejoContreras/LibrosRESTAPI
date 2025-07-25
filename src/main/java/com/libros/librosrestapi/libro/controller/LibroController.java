package com.libros.librosrestapi.libro.controller;

import com.libros.librosrestapi.libro.DTO.input.*;
import com.libros.librosrestapi.libro.DTO.output.LibroUpdateResponseDTO;
import com.libros.librosrestapi.libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.libro.mapper.ILibroMapper;
import com.libros.librosrestapi.libro.service.LibroService;
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
        LibroResponseDTO addLibro = libroService.addLibro(libroCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(addLibro);
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
