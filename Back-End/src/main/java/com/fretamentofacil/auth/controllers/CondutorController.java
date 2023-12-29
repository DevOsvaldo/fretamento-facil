package com.fretamentofacil.auth.controllers;

import com.fretamentofacil.auth.domain.dto.CondutorDTO;
import com.fretamentofacil.auth.domain.dto.CondutorPageDTO;
import com.fretamentofacil.auth.domain.model.Condutor;
import com.fretamentofacil.auth.services.CondutorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/condutor")
public class CondutorController {
    @Autowired
    private CondutorService condutorService;
    //GET
    @GetMapping
    public CondutorPageDTO list(@RequestParam(defaultValue = "0")@PositiveOrZero int page,
                                @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize){
        return condutorService.list(page, pageSize);
    }
    /*@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Condutor> findall(){
        return condutorService.findAllActiveCondutores();
    }*/
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Condutor> getCondutorById(@PathVariable Long id){
        Optional<Condutor> condutorOptional = condutorService.getCondutorById(id);
        return condutorOptional.map(condutor -> new ResponseEntity<>(condutor, HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/excluidos")//Endpoint para obter a lista de Condutores marcados como Excluidos
    public ResponseEntity<List<Condutor>> listarCondutoresExcluidos(){
        List<Condutor> condutoresExcluidos = condutorService.listarCondutoresExcluidos();
        return new ResponseEntity<>(condutoresExcluidos, HttpStatus.OK);
    }
    //POST
    @PostMapping("/cadastroUser")
    @ResponseStatus(code=HttpStatus.CREATED)
    public Condutor condutorNovoUser(@RequestBody @Valid CondutorDTO condutor){
        return condutorService.criarNovoUserCondutor(condutor);

    }
    @PostMapping("/carregar")
    public Condutor carregarCarga(@RequestParam Long condutorId, @RequestParam Long cargaId){
        return condutorService.carregarCarga(condutorId, cargaId);
    }

    //PUT
    @PutMapping("/editar/{id}")
    public ResponseEntity<Condutor> atualizarCondutor(@PathVariable Long id, @RequestBody CondutorDTO condutorDTO) {
        try {
            Condutor condutorAtualizado = condutorService.atualizarDadosCondutor(id, condutorDTO);
            return ResponseEntity.ok(condutorAtualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/situacao/{id}")
    public ResponseEntity<Condutor> modificarSituacao(@PathVariable Long id, @RequestBody Condutor condutorSituacao){
       return condutorService.getCondutorById(id).map(condutor -> {
           condutor.setSituacaoCondutor(condutorSituacao.getSituacaoCondutor());
           Condutor condutorAtualizado = condutorService.atualizarCondutor(id,condutor);
           return ResponseEntity.ok().body(condutorAtualizado);
       }).orElse(ResponseEntity.notFound().build());

    }
    @PutMapping("/reativar/{id}")
    public ResponseEntity<Condutor> reativarCondutor(@PathVariable Long id){
        Condutor condutorReativado = condutorService.reativarCondutor(id);
        return new ResponseEntity<>(condutorReativado, HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public void deletarCondutor(@PathVariable  Long id) {
        condutorService.softDeleteCondutorById(id);
    }

}
