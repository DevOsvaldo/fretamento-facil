package com.fretamentofacil.auth.controllers;

import com.fretamentofacil.auth.domain.dto.GestorDTO;
import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.domain.model.Gestor;
import com.fretamentofacil.auth.infra.security.messageResponse.InformacoesCarregamentoResponse;
import com.fretamentofacil.auth.services.CargaService;
import com.fretamentofacil.auth.services.CondutorService;
import com.fretamentofacil.auth.services.GestorService;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<Integer> getGestorIdByUserId(@RequestParam Long userId) {
        Integer gestorId = gestorService.getGestorIdByUserId(userId);
        if (gestorId != null) {
            return ResponseEntity.ok(gestorId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public Gestor findById(@PathVariable Long id){
        return  gestorService.findById(id);
    }
    @GetMapping
    public List<Gestor> findAll(){
        return gestorService.buscarGestor();
    }
    @PostMapping("/cadastro")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Gestor criarPerfilAdmin(@RequestBody GestorDTO gestor){
        return gestorService.criandoNovoUserGestor(gestor);
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
    public ResponseEntity<InformacoesCarregamentoResponse> obterInformacoesCarregamento() {
        try {
            InformacoesCarregamentoResponse informacoesCarregamento = gestorService.obterInformacoesCarregamento();
            return ResponseEntity.ok(informacoesCarregamento);
        } catch (Exception e) {
            // Se ocorrer um erro ao obter informações, você pode retornar um status de erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @DeleteMapping("/condutor/{id}")
    public void deletarCondutorById(@PathVariable  Long id) {
        condutorService.deletarCondutorById(id);
    }

}
