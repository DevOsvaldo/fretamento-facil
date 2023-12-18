package com.projetoJwt.auth.controllers;

import com.projetoJwt.auth.domain.model.Carga;
import com.projetoJwt.auth.domain.model.Condutor;
import com.projetoJwt.auth.services.CondutorService;
import jakarta.validation.Valid;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Condutor> findall(){
        return condutorService.findAllCondutor();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Condutor> getCondutorById(@PathVariable Long id){
        Optional<Condutor> condutorOptional = condutorService.getCondutorById(id);
        return condutorOptional.map(condutor -> new ResponseEntity<>(condutor, HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/cadastro")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Condutor cadastroMotorista(@RequestBody @Valid Condutor condutor){
        return condutorService.criarNovoCondutor(condutor);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Condutor> modificarCadastro(@PathVariable Long id, @RequestBody @Valid Condutor condutor) {
        try {
            Condutor condutorAtualizado = condutorService.atualizarCondutor(id, condutor);
            return new ResponseEntity<>(condutorAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/carregar")
    public Condutor carregarCarga(@RequestParam Long condutorId, @RequestParam Long cargaId){
        return condutorService.carregarCarga(condutorId, cargaId);
    }

}
