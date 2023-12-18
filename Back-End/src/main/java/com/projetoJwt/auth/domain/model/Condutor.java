package com.projetoJwt.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String tipo_Veiculo;
    private Double capacidadeVeiculo;

    @Enumerated(EnumType.STRING)
    private SituacaoCondutor situacaoCondutor;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "condutor_produto_carga",
            joinColumns = @JoinColumn(name = "condutor_id"),
            inverseJoinColumns = @JoinColumn(name = "produtoCarga_id"))
    private List<Carga> carga ;


    /*
    public List<Carga> getCarga() {
        return carga;
    }
    public void setCarga(List<Carga> carga) {
        this.carga = carga;


    }


     */
}
