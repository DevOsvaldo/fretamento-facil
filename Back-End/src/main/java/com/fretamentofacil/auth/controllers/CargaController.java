package com.fretamentofacil.auth.controllers;

import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.domain.model.SituacaoCarga;
import com.fretamentofacil.auth.services.CargaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cargas")
public class CargaController {

    @Autowired
    private final CargaService cargaService;
    @Autowired
    public CargaController(CargaService cargaService) {
        this.cargaService = cargaService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Carga> findallCarga(){
        return cargaService.findAllCarga();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Carga> getById(@PathVariable Long id){
        Optional<Carga> cargaOptional = cargaService.getCargaById(id);
        return cargaOptional.map(carga -> new ResponseEntity<>(carga, HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/cadastro")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Carga criandoNovaCarga(@RequestBody @Valid Carga carga){
       carga.setSituacaoCarga(SituacaoCarga.INATIVA);
        return cargaService.criandoNovaCarga(carga);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Carga> modificarCarga(@PathVariable Long id, @RequestBody Carga cargaNova){
        try { Carga cargaAtualizada = cargaService.atualizarCargaById(id, cargaNova);
            return new ResponseEntity<>(cargaAtualizada, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}")
    public void deletarCarga(@PathVariable Long id){
        cargaService.deleteCarga(id);
    }

}
