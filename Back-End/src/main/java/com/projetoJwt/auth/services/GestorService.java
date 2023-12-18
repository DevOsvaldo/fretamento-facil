package com.projetoJwt.auth.services;


import com.projetoJwt.auth.domain.model.*;
import com.projetoJwt.auth.repositories.CargaRepository;
import com.projetoJwt.auth.repositories.CondutorRepository;
import com.projetoJwt.auth.repositories.GestorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GestorService {
    private final GestorRepository gestorRepository;
    private final CondutorRepository condutorRepository;
    private final CargaRepository cargaRepository;

    public GestorService(GestorRepository gestorRepository, CondutorRepository condutorRepository, CargaRepository cargaRepository) {
        this.gestorRepository = gestorRepository;
        this.condutorRepository = condutorRepository;
        this.cargaRepository = cargaRepository;
    }

    public Gestor cadastrarPerfilAdmin(Gestor gestor) {
        return gestorRepository.save(gestor);
    }
    public List<Gestor> buscarGestor(){
        return gestorRepository.findAll();
    }
    public Gestor inserirCarga(Long cargaId, Long gestorId){
        Gestor gestor = gestorRepository.findById(gestorId).
                orElseThrow(()-> new RuntimeException("Perfil a não encontrada para o ID: " + gestorId + "."));
        Carga carga = cargaRepository.findById(cargaId).
                orElseThrow(()-> new RuntimeException("Carga não encontrada para o ID: " + cargaId + "."));
        if (carga.getSituacaoCarga().equals(SituacaoCarga.INATIVA)){
            carga.setSituacaoCarga(SituacaoCarga.AGUARDANDO);
            cargaRepository.save(carga);
        } else{
            throw new RuntimeException("Carga já aguardando atendimento!");
        }
        if (gestor.getCargas() == null){
            gestor.setCargas(new ArrayList<>());
        }
        gestor.getCargas().add(carga);
        carga.setGestor(gestor);
        return gestorRepository.save(gestor);
    }
    public Carga alterarCarga(Long cargaId, Carga cargaModificada) {
        Carga cargaExiste = cargaRepository.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("ProdutoCarga não encontrada para o ID: " + cargaId + "."));
        BeanUtils.copyProperties(cargaModificada, cargaExiste, "id");
        return cargaRepository.save(cargaExiste);
    }
    public String obterInformacoesCarregamento(){
        List<Carga> cargaList = cargaRepository.findBySituacaoCarga(SituacaoCarga.ATENDIDA);
        List<Condutor> condutorList = condutorRepository.findBySituacaoCondutor(SituacaoCondutor.CARREGANDO);
        StringBuilder informacoes = new StringBuilder();
        if (cargaList != null && !cargaList.isEmpty()){
            informacoes.append("Cargas sendo Carregadas: \n");
            for(Carga carga : cargaList){
                informacoes.append("Carga ID:").append(carga.getId()).append("\n");
                informacoes.append("Nome do Cliente: ").append(carga.getNomeCliente()).append("\n");
                informacoes.append("Peso da Carga: ").append(carga.getPesoCarga()).append("\n");
            }
        }else {
            informacoes.append("Nenhuma carga está sendo carregada atualmente.\n");}

        if (condutorList != null && !condutorList.isEmpty()){
            for (Condutor condutorPerfil : condutorList){
                informacoes.append("Condutor Nome: ").append(condutorPerfil.getNome()).append("\n");
                informacoes.append("Tipo de Veiculo: ").append(condutorPerfil.getTipo_Veiculo()).append("\n");
                informacoes.append("Capacidade do Veiculo: ").append(condutorPerfil.getCapacidadeVeiculo()).append("\n");

            }
        } else {
            informacoes.append("Nenhuma carga está sendo carregada atualmente.\n");
        }
        return informacoes.toString();
    }
}
