package com.libros.librosrestapi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libros.librosrestapi.libro.DTO.input.LibroCreateRequestDTO;
import com.libros.librosrestapi.libro.DTO.input.LibroUpdateRequestDTO;
import com.libros.librosrestapi.libro.entity.LibroEntity;
import com.libros.librosrestapi.libro.repository.LibroRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LibroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibroRepo libroRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private LibroEntity savedLibro;

    @BeforeEach
    void setUp() {
        libroRepo.deleteAll(); // Limpia la tabla antes de cada test
        savedLibro = libroRepo.save(new LibroEntity(null, "Título inicial", "Autor inicial", "978-3-16-148410-0"));
    }

    @Test
    void testGetLibros() throws Exception {
        mockMvc.perform(get("/api/v1/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Devuelve 1 libro
                .andExpect(jsonPath("$[0].titulo", is("Título inicial")));
    }

    @Test
    void testGetLibroById() throws Exception {
        mockMvc.perform(get("/api/v1/libros/{id}", savedLibro.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Título inicial")));
    }

    @Test
    void testCreateLibro() throws Exception {
        LibroCreateRequestDTO createDTO = new LibroCreateRequestDTO("Nuevo Título", "Nuevo Autor", "1234567891234", "Planeta");

        mockMvc.perform(post("/api/v1/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo", is("Nuevo Título")));
    }

    @Test
    void testUpdateLibro() throws Exception {
        LibroUpdateRequestDTO updateDTO = new LibroUpdateRequestDTO("Título actualizado");

        mockMvc.perform(put("/api/v1/libros/{id}", savedLibro.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Título actualizado")));
    }

    @Test
    void testDeleteLibro() throws Exception {
        mockMvc.perform(delete("/api/v1/libros/{id}", savedLibro.getId()))
                .andExpect(status().isNoContent());

        // Verifica que realmente se eliminó
        mockMvc.perform(get("/api/v1/libros/{id}", savedLibro.getId()))
                .andExpect(status().isNotFound());
    }
}