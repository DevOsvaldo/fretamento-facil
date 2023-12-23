package com.projetoJwt.auth.controllers;

import com.projetoJwt.auth.domain.model.Carga;
import com.projetoJwt.auth.domain.model.Gestor;
import com.projetoJwt.auth.services.CargaService;
import com.projetoJwt.auth.services.CondutorService;
import com.projetoJwt.auth.services.GestorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestor")
public class GestorController {
    private final GestorService gestorService;
    private final CargaService cargaService;
    private final CondutorService condutorService;

    public GestorController(GestorService gestorService, CargaService cargaService, CondutorService condutorService) {
        this.gestorService = gestorService;
        this.cargaService = cargaService;
        this.condutorService = condutorService;
    }


    @GetMapping
    public List<Gestor> findAll(){
        return gestorService.buscarGestor();
    }
    @PostMapping("/cadastro")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Gestor criarPerfilAdmin(@RequestBody Gestor gestor){


        return gestorService.cadastrarPerfilAdmin(gestor);
    }
    @PostMapping("/inserir")
    public Gestor inserirCarga(@RequestParam Long cargaId, Long gestorId){
        return gestorService.inserirCarga(cargaId, gestorId);
    }
    @PutMapping("/modificar/{cargaId}")
    public Carga modificarCarga(@PathVariable Long cargaId, Carga  cargaModificada){
        return  gestorService.alterarCarga(cargaId, cargaModificada);

    }

    @GetMapping("/info")
    public ResponseEntity<String> obterInformacoesCarregamento(){
        String informacoesCarregamento = gestorService.obterInformacoesCarregamento();
        return ResponseEntity.ok(informacoesCarregamento);
    }

    @DeleteMapping("/condutor/{id}")
    public void deletarCondutorById(@PathVariable  Long id) {
        condutorService.deletarCondutorById(id);
    }

}
