package com.fretamentofacil.auth.services;

import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.repositories.CargaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CargaService {

    @Autowired
    private final CargaRepository cargaRepository;

    public CargaService(CargaRepository cargaRepository) {
        this.cargaRepository = cargaRepository;
    }

    public List<Carga> findAllCarga(){
        return cargaRepository.findAll();
    }
    public Optional<Carga> getCargaById(Long id){
        return cargaRepository.findById(id);
    }

    //Salvando a carga
    public Carga criandoNovaCarga(Carga carga){
        return cargaRepository.save(carga);
    }
    public Carga atualizarCargaById(Long id, Carga cargaModificada){
        Carga existeCarga = cargaRepository.findById(id).
                orElseThrow(
                        ()->new RuntimeException("Carga não encontada com este ID: "+id));
        BeanUtils.copyProperties(cargaModificada, existeCarga, "id");
        return cargaRepository.save(existeCarga);
    }
    //Deletando Carga pelo ID
    public void deleteCarga(Long id){
        if(cargaRepository.existsById(id)){
            cargaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Carga não encontrada com este ID:"+id);
        }

    }
}
