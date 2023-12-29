package com.fretamentofacil.auth.domain.dto;


import com.fretamentofacil.auth.domain.model.SituacaoCondutor;
import com.fretamentofacil.auth.domain.user.UserRole;

public record CondutorDTO(Long id,String login, String password, UserRole role, String nome, String cpf,String cep,
                          String endereco, String tipo_Veiculo , Double capacidadeVeiculo,
                          SituacaoCondutor situacaoCondutor) {


}
