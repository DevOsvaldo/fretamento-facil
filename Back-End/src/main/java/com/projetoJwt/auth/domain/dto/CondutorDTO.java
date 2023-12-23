package com.projetoJwt.auth.domain.dto;


import com.projetoJwt.auth.domain.model.SituacaoCondutor;
import com.projetoJwt.auth.domain.user.UserRole;

public record CondutorDTO(String login, String password, UserRole role, String nome, String cpf, String endereco,
                          String tipo_Veiculo , Double capacidadeVeiculo, SituacaoCondutor situacaoCondutor) {

}
