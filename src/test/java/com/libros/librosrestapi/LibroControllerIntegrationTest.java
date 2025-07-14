package com.libros.librosrestapi;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import com.libros.librosrestapi.Libro.DTO.output.LibroResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibroControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/libros";
    }

    @Test
    void flujoCompletoCrearActualizarEliminarLibro() {
        String baseUrl = getBaseUrl();

        // 1️⃣ Crear libro
        LibroRequestDTO nuevoLibro = new LibroRequestDTO(0, "Título original", "Autor original", "978-3-16-148410-0");
        ResponseEntity<LibroRequestDTO> createResponse = restTemplate.postForEntity(baseUrl, nuevoLibro, LibroRequestDTO.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        LibroRequestDTO creado = createResponse.getBody();
        assertNotNull(creado);
        assertTrue(creado.getId() > 0);
        assertEquals("Título original", creado.getTitulo());

        // 2️⃣ Actualizar libro
        creado.setTitulo("Título actualizado");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LibroRequestDTO> updateRequest = new HttpEntity<>(creado, headers);

        ResponseEntity<LibroRequestDTO> updateResponse = restTemplate.exchange(
                baseUrl + "/" + creado.getId(),
                HttpMethod.PUT,
                updateRequest,
                LibroRequestDTO.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        LibroRequestDTO actualizado = updateResponse.getBody();
        assertNotNull(actualizado);
        assertEquals("Título actualizado", actualizado.getTitulo());

        // 3️⃣ Obtener libro actualizado
        LibroResponseDTO obtenido = restTemplate.getForObject(baseUrl + "/" + creado.getId(), LibroResponseDTO.class);

        assertNotNull(obtenido);
        assertEquals("Título actualizado", obtenido.getTitulo());
        assertEquals("Autor original", obtenido.getAutor());
        assertEquals("978-3-16-148410-0", obtenido.getIsbn());

        // 4️⃣ Eliminar libro
        restTemplate.delete(baseUrl + "/" + creado.getId());

        // 5️⃣ Verificar que ya no existe
        ResponseEntity<String> getAfterDelete = restTemplate.getForEntity(baseUrl + "/" + creado.getId(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, getAfterDelete.getStatusCode());
    }
}
