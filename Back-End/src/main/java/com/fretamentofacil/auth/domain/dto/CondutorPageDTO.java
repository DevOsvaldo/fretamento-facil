package com.fretamentofacil.auth.domain.dto;

import java.util.List;

public record CondutorPageDTO(List<CondutorDTO> condutor,long totalElements, int totalPages) {
}
