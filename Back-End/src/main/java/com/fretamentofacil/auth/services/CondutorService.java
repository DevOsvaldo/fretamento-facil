package com.fretamentofacil.auth.services;

import com.fretamentofacil.auth.domain.dto.CondutorDTO;
import com.fretamentofacil.auth.domain.dto.CondutorPageDTO;
import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.domain.model.SituacaoCondutor;
import com.fretamentofacil.auth.repositories.CondutorRepository;
import com.fretamentofacil.auth.repositories.RoleRepository;
import com.fretamentofacil.auth.repositories.UserRepository;
import com.fretamentofacil.auth.domain.model.Condutor;
import com.fretamentofacil.auth.domain.model.SituacaoCarga;
import com.fretamentofacil.auth.domain.user.Role;
import com.fretamentofacil.auth.domain.user.User;
import com.fretamentofacil.auth.domain.user.UserRole;
import com.fretamentofacil.auth.repositories.CargaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.fretamentofacil.auth.controllers.AuthenticationController.LOGGER;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private  CargaRepository cargaRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;



    public CondutorPageDTO list(@PositiveOrZero int page, @Positive @Max(100) int pageSize){
        Page<Condutor> pageCondutor = condutorRepository.findAll(PageRequest.of(page,pageSize));
        List<CondutorDTO> condutores = pageCondutor.getContent().stream()
                .map(this::mapCondutorToDTO)
                .collect(Collectors.toList());
        return new CondutorPageDTO(condutores, pageCondutor.getTotalElements(), pageCondutor.getTotalPages());
    }
    private CondutorDTO mapCondutorToDTO(Condutor condutor) {
        // Aqui, obtenha o User associado ao Condutor e recupere login, password(senha) e role
        String login = condutor.getUser().getLogin();
        String password = condutor.getUser().getPassword();
        UserRole role = condutor.getRoles().stream()
                .findFirst()
                .map(Role::getRoleName) // Use map para extrair o roleName se o Optional não estiver vazio
                .orElse(null);

        return new CondutorDTO(
                condutor.getId(),
                login,
                password,
                role,
                condutor.getNome(),
                condutor.getCpf(),
                condutor.getCep(),
                condutor.getEndereco(),
                condutor.getTipo_Veiculo(),
                condutor.getCapacidadeVeiculo(),
                condutor.getSituacaoCondutor()
        );
    }
    /*
    public List<Condutor> findAllCondutor() {

        return condutorRepository.findAll();
    }*/
    /*
    private CondutorDTO mapCondutorToDTO(Condutor condutor) {
        return new CondutorDTO(
                condutor.getLogin(),
                condutor.getPassword(),
                condutor.getRole(),
                condutor.getNome(),
                condutor.getCpf(),
                condutor.getEndereco(),
                condutor.getCep(),
                condutor.getTipo_Veiculo(),
                condutor.getCapacidadeVeiculo(),
                condutor.getSituacaoCondutor()
        );
    }*/
    public List<Condutor> findAllActiveCondutores() {
        return condutorRepository.findByDeletedFalse();
    }
    public Optional<Condutor> getCondutorById(Long id){
        return condutorRepository.findById(id);
    }

    public Condutor criarNovoUserCondutor(CondutorDTO condutorDto){
       if (repository.findByLogin(condutorDto.login()) != null) {
            return null;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(condutorDto.password());
        Role userRole = roleRepository.findByRoleName(UserRole.USER)
                .orElseGet(() -> roleRepository.save(new Role(UserRole.USER)));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        //Salva o novo Condutor no banco de dados
        User user = new User(condutorDto.login(), encryptedPassword, condutorDto.role());
        user.setRoles(roles);





        Condutor condutor = new Condutor();
        condutor.setNome(condutorDto.nome());
        condutor.setCep(condutorDto.cep());
        condutor.setEndereco(condutorDto.endereco());
        condutor.setUser(user);
        condutor.setCpf(condutorDto.cpf());
        condutor.setTipo_Veiculo(condutorDto.tipo_Veiculo());
        condutor.setCapacidadeVeiculo(condutorDto.capacidadeVeiculo());
        condutor.setSituacaoCondutor(condutorDto.situacaoCondutor());
        condutor.setRoles(roles);
        user.setCondutor(condutor);

        // Salvar o Condutor
        return condutorRepository.save(condutor);
    }

    public Condutor atualizarDadosCondutor(Long id, CondutorDTO condutorDTO) {
        // Obter o Condutor do banco de dados
        Condutor condutor = condutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condutor não encontrado para o ID: " + id));

        // Atualizar apenas as propriedades específicas do Condutor
        condutor.setNome(condutorDTO.nome());
        condutor.setCpf(condutorDTO.cpf());
        condutor.setCep(condutorDTO.cep());
        condutor.setEndereco(condutorDTO.endereco());
        condutor.setTipo_Veiculo(condutorDTO.tipo_Veiculo());
        condutor.setCapacidadeVeiculo(condutorDTO.capacidadeVeiculo());
        condutor.setSituacaoCondutor(condutorDTO.situacaoCondutor());
        // Adicione outras propriedades do Condutor que você deseja atualizar

        // Salvar as alterações no Condutor
        return condutorRepository.save(condutor);
    }

    public Condutor atualizarCondutor(@NotNull @Positive Long id, @Valid Condutor condutorModificado){
        Condutor condutor = condutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condutor não encontrada para o ID: " + id + "."));
        BeanUtils.copyProperties(condutorModificado, condutor, "id","login","password");

        LOGGER.info("UPDATE: "+condutor);
        return condutorRepository.save(condutor);
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

    public void softDeleteCondutorById(Long id) {
        Condutor condutor = condutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condutor não encontrado para o ID: " + id));

        // Marcar o Condutor como excluído
        condutor.setDeleted(true);
        condutorRepository.save(condutor);
    }
    public List<Condutor> listarCondutoresExcluidos(){
        return condutorRepository.findByDeletedTrue();
    }
    public Condutor reativarCondutor(Long id){
        Condutor condutor = condutorRepository.findById(id).orElseThrow(()->
                new RuntimeException("Condutor não encontrado!"));

        //Verificação se o condutor está marcado como excluido
        if(!condutor.isDeleted()){
            throw new RuntimeException("O condutor já está ativo!");
        }
        //Atualizando o status para ativo
        condutor.setDeleted(false);

        //Salvar as alterações no Condutor
        return condutorRepository.save(condutor);
    }
    //deletando condutor
    public void deletarCondutorById(Long id) {
        condutorRepository.delete(condutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sem id"+id)));

    }
}



