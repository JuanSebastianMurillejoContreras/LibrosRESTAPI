package com.libros.librosrestapi.Libro.controller;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
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

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> getLibros() {
        List<LibroResponseDTO> libros = libroService.getLibros();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> getLibroById(@PathVariable("id") int id) {
        LibroResponseDTO libro = libroService.getLibro(id);
        return ResponseEntity.ok(libro);
    }


    @PostMapping()
    public ResponseEntity<LibroRequestDTO> createLibro(@RequestBody @Valid LibroRequestDTO libroRequestDTO) {
        LibroRequestDTO addLibro = libroService.addLibro(libroRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLibro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroRequestDTO> updateLibro(@PathVariable int id,
                                                       @RequestBody @Valid LibroRequestDTO libroRequestDTO) {
        libroRequestDTO.setId(id);
        LibroRequestDTO updateLibro = libroService.updateLibro(libroRequestDTO);
        return ResponseEntity.ok(updateLibro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable int id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }

}
