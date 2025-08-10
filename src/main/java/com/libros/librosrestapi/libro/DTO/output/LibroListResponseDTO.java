package com.libros.librosrestapi.libro.DTO.output;

import java.util.List;

public record LibroListResponseDTO (
   List<LibroResponseDTO> libroResponseDTOList
) {}
