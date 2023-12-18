package com.projetoJwt.auth.services;

import com.projetoJwt.auth.domain.model.Carga;
import com.projetoJwt.auth.domain.model.Condutor;
import com.projetoJwt.auth.domain.model.SituacaoCarga;
import com.projetoJwt.auth.domain.model.SituacaoCondutor;
import com.projetoJwt.auth.repositories.CargaRepository;
import com.projetoJwt.auth.repositories.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CondutorService {
    @Autowired
    private final CondutorRepository condutorRepository;
    @Autowired
    private final CargaRepository cargaRepository;


    public CondutorService(CondutorRepository condutorRepository, CargaRepository cargaRepository) {
        this.condutorRepository = condutorRepository;
        this.cargaRepository = cargaRepository;
    }

    public List<Condutor> findAllCondutor(){
        return condutorRepository.findAll();
    }

    public Optional<Condutor> getCondutorById(Long id){
        return condutorRepository.findById(id);
    }
    public Condutor criarNovoCondutor(Condutor condutor){
        return condutorRepository.save(condutor);
    }
    public Condutor atualizarCondutor(Long id, Condutor condutorModificado){
        if(condutorRepository.existsById(id)){
            condutorModificado.setId(id);
            return condutorRepository.save(condutorModificado);
        } else {
            throw new RuntimeException("Condutor não encontrado, ID:"+id+" não existe");
        }

    }
    public Condutor carregarCarga(Long condutorId, Long cargaId){
        try {
            Condutor condutor = condutorRepository.findById(condutorId)
                    .orElseThrow(()->new RuntimeException("Condutor não encontrado para o ID: "+condutorId+"."));
            Carga carga = cargaRepository.findById(cargaId).
                    orElseThrow(()->new RuntimeException("Carga não encontrada para o Id: "+cargaId+"."));

            if (carga.getSituacaoCarga() == SituacaoCarga.AGUARDANDO &&
                    condutor.getSituacaoCondutor() == SituacaoCondutor.AGUARDANDO){
                Double capacidadeVeiculo = condutor.getCapacidadeVeiculo();
                Double pesoCarga = carga.getPesoCarga();
                if (capacidadeVeiculo >= pesoCarga){
                    carga.setSituacaoCarga(SituacaoCarga.ATENDIDA);
                    condutor.setSituacaoCondutor(SituacaoCondutor.CARREGANDO);

                    cargaRepository.save(carga);

                    List<Carga> cargas = condutor.getCarga();
                    cargas.add(carga);
                    condutor.setCarga(cargas);
                    carga.setCondutor(condutor);
                    condutor = condutorRepository.save(condutor);
                } else {
                    throw  new RuntimeException("Capacidade de carga do veiculo insuficiente para carregar a carga!");
                }
            } else {
                throw new RuntimeException("Carga não existe ou não está habilitada para ser carregada!");
            }
            return condutor;
        }catch (RuntimeException ex){
            throw new RuntimeException("Não foi possível carregar o produto. Motivo: " + ex.getMessage());
        }
    }
    //deletando condutor
    public void deletarCondutor(Long id){
        if(condutorRepository.existsById(id)){
            condutorRepository.deleteById(id);
        } else {
            throw  new RuntimeException("Condutor não encontrado com ID: "+id);
        }
    }
}
