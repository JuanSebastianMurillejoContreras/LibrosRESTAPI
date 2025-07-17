package com.libros.librosrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import com.libros.librosrestapi.Libro.controller.LibroController;
import com.libros.librosrestapi.Libro.exception.LibroNotFoundException;
import com.libros.librosrestapi.Libro.service.LibroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getLibros_devuelveLista() throws Exception {
        LibroResponseDTO dto = new LibroResponseDTO(1, "Titulo", "Autor", "isbn-123");
        when(libroService.getLibros()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Titulo"));
    }

    @Test
    void getLibroById_existente_devuelveLibro() throws Exception {
        LibroResponseDTO dto = new LibroResponseDTO(1, "Titulo", "Autor", "isbn-123");
        when(libroService.getLibro(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/libros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Titulo"));
    }

    @Test
    void getLibroById_noExistente_devuelve404() throws Exception {
        when(libroService.getLibro(99)).thenThrow(new LibroNotFoundException("404", "No existe"));

        mockMvc.perform(get("/api/v1/libros/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createLibro_valido_devuelve201() throws Exception {
        LibroRequestDTO request = new LibroRequestDTO(0, "Titulo", "Autor", "978-3-16-148410-0");
        LibroRequestDTO saved = new LibroRequestDTO(1, "Titulo", "Autor", "978-3-16-148410-0");

        when(libroService.addLibro(any(LibroRequestDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Titulo"));
    }

    @Test
    void createLibro_invalido_devuelve400() throws Exception {
        // Falta titulo
        LibroRequestDTO request = new LibroRequestDTO(0, "", "Autor", "978-3-16-148410-0");

        mockMvc.perform(post("/api/v1/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateLibro_valido_devuelve200() throws Exception {
        LibroRequestDTO request = new LibroRequestDTO(1, "Nuevo Titulo", "Autor", "978-3-16-148410-0");
        LibroRequestDTO updated = new LibroRequestDTO(1, "Nuevo Titulo", "Autor", "978-3-16-148410-0");

        when(libroService.updateLibro(any(LibroRequestDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Nuevo Titulo"));
    }

    @Test
    void deleteLibro_devuelve204() throws Exception {
        mockMvc.perform(delete("/api/v1/libros/1"))
                .andExpect(status().isNoContent());
    }
}
