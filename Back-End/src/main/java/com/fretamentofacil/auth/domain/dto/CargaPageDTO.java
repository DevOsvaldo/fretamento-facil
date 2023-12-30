package com.fretamentofacil.auth.domain.dto;

import java.util.List;

public record CargaPageDTO(List<CargaDTO> carga, long totalElements, int totalPages) {
}
