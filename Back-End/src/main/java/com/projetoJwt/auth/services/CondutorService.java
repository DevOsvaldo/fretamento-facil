package com.projetoJwt.auth.services;

import com.projetoJwt.auth.domain.model.Carga;
import com.projetoJwt.auth.domain.model.Condutor;
import com.projetoJwt.auth.domain.model.SituacaoCarga;
import com.projetoJwt.auth.domain.model.SituacaoCondutor;
import com.projetoJwt.auth.domain.dto.CondutorDTO;
import com.projetoJwt.auth.domain.user.Role;
import com.projetoJwt.auth.domain.user.User;
import com.projetoJwt.auth.domain.user.UserRole;
import com.projetoJwt.auth.repositories.CargaRepository;
import com.projetoJwt.auth.repositories.CondutorRepository;
import com.projetoJwt.auth.repositories.RoleRepository;
import com.projetoJwt.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CondutorService {
    @Autowired
    private  CondutorRepository condutorRepository;
    @Autowired
    private  CargaRepository cargaRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    private void createRoleIfNotFound(UserRole role) {
        Optional<Role> optionalRole = roleRepository.findByRoleName(role);
        if (optionalRole.isEmpty()) {
            roleRepository.save(new Role(role));
        }
    }


    public List<Condutor> findAllCondutor(){
        return condutorRepository.findAll();
    }

    public Optional<Condutor> getCondutorById(Long id){
        return condutorRepository.findById(id);
    }
    public Condutor criarNovoCondutor(Condutor condutor){

        // Salva o novo Condutor no banco de dados
        return condutorRepository.save(condutor);
    }
    public Condutor criarNovoUserCondutor(CondutorDTO condutor){
        if (repository.findByLogin(condutor.login()) != null) {
            return null;
        }
        createRoleIfNotFound(UserRole.USER);
        String encryptedPassword = new BCryptPasswordEncoder().encode(condutor.password());
        Role userRole = roleRepository.findByRoleName(UserRole.USER)
                .orElseThrow(() -> new RuntimeException("Error: role USER não encontrada"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        // Salva o novo Condutor no banco de dados
        User user = new User(condutor.login(), encryptedPassword, condutor.role());
        user.setRoles(roles);

        repository.save(user);
        Condutor condutor1 = new Condutor();
        condutor1.setNome(condutor.nome());
        condutor1.setEndereco(condutor.endereco());
        condutor1.setUser(user);
        condutor1.setCpf(condutor.cpf());
        condutor1.setTipo_Veiculo(condutor.tipo_Veiculo());
        condutor1.setCapacidadeVeiculo(condutor.capacidadeVeiculo());
        condutor1.setSituacaoCondutor(condutor.situacaoCondutor());
        user.setCondutor(condutor1);
        condutor1.setRoles(roles);
        return condutorRepository.save(condutor1);

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
    public void deletarCondutorById(Long id){
        if(condutorRepository.existsById(id)){
            condutorRepository.deleteById(id);
        } else {
            throw  new RuntimeException("Condutor não encontrado com ID: "+id);
        }
    }

    }

