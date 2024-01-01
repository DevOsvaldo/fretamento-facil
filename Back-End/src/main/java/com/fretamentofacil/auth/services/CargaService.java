package com.fretamentofacil.auth.services;

import com.fretamentofacil.auth.domain.dto.CargaDTO;
import com.fretamentofacil.auth.domain.dto.CargaPageDTO;
import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.domain.model.Condutor;
import com.fretamentofacil.auth.repositories.CargaRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargaService {

    @Autowired
    private final CargaRepository cargaRepository;

    public CargaService(CargaRepository cargaRepository) {
        this.cargaRepository = cargaRepository;
    }


    public CargaPageDTO list(@PositiveOrZero int page,@Positive @Max(100) int pageSize){
        Page<Carga> pageCarga = cargaRepository.findAll(PageRequest.of(page, pageSize));
        List<CargaDTO> cargas = pageCarga.getContent().stream().map(this::mapCargaDTO).collect(Collectors.toList());
        return new CargaPageDTO(cargas,pageCarga.getTotalElements(),pageCarga.getTotalPages());
    }
    private CargaDTO mapCargaDTO(Carga carga){
        return  new CargaDTO(
                carga.getId(),
                carga.getNomeCliente(),
                carga.getEnderecoCliente(),
                carga.getPesoCarga(),
                carga.getValorFrete(),
                carga.getTipoVeiculo(),
                carga.getSituacaoCarga()
        );
    }
    /*public List<Carga> findAllCarga(){
        return cargaRepository.findAll();
    }*/
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
