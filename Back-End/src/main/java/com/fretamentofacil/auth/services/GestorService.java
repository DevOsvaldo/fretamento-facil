package com.fretamentofacil.auth.services;


import com.fretamentofacil.auth.domain.dto.GestorDTO;
import com.fretamentofacil.auth.domain.model.*;
import com.fretamentofacil.auth.infra.security.messageResponse.InformacoesCargaCondutorResponse;
import com.fretamentofacil.auth.infra.security.messageResponse.InformacoesCarregamentoResponse;
import com.fretamentofacil.auth.repositories.*;

import com.fretamentofacil.auth.domain.user.Role;
import com.fretamentofacil.auth.domain.user.User;
import com.fretamentofacil.auth.domain.user.UserRole;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestorService {
    private final GestorRepository gestorRepository;
    private final CondutorRepository condutorRepository;
    private final CargaRepository cargaRepository;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private  UserService userService;

    public GestorService(GestorRepository gestorRepository, CondutorRepository condutorRepository,
                         CargaRepository cargaRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.gestorRepository = gestorRepository;
        this.condutorRepository = condutorRepository;
        this.cargaRepository = cargaRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public Gestor criandoNovoUserGestor(GestorDTO gestorDTO) {
        if (userRepository.findByLogin(gestorDTO.login()) != null) {
            return null;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(gestorDTO.password());
        Role userRole = roleRepository.findByRoleName(UserRole.MOD).
                orElseGet(() -> roleRepository.save(new Role(UserRole.MOD)));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = new User(gestorDTO.login(),encryptedPassword,gestorDTO.role());
        user.setRoles(roles);


        Gestor gestor = new Gestor();
        gestor.setNome(gestorDTO.nome());
        gestor.setCpf(gestorDTO.cpf());
        gestor.setCargo(gestorDTO.cargo());
        gestor.setUser(user);
        gestor.setRoles(roles);
        user.setGestor(gestor);

        return gestorRepository.save(gestor);


    }

    public Gestor findById(Long id){
        return gestorRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Perfil não encontrado"));
    }
    public List<Gestor> buscarGestor(){
        return gestorRepository.findAll();
    }
    public Gestor inserirCarga(Long cargaId, Long gestorId){
        Gestor gestor = gestorRepository.findById(gestorId).
                orElseThrow(()-> new RuntimeException("Perfil a não encontrada para o ID: " + gestorId + "."));
        Carga carga = cargaRepository.findById(cargaId).
                orElseThrow(()-> new RuntimeException("Carga não encontrada para o ID: " + cargaId + "."));
        if (carga.getSituacaoCarga().equals(SituacaoCarga.INATIVA)){
            carga.setSituacaoCarga(SituacaoCarga.AGUARDANDO);
            cargaRepository.save(carga);
        } else{
            throw new RuntimeException("Carga já aguardando atendimento!");
        }
        if (gestor.getCargas() == null){
            gestor.setCargas(new ArrayList<>());
        }
        gestor.getCargas().add(carga);
        carga.setGestor(gestor);
        return gestorRepository.save(gestor);
    }
    public Carga alterarCarga(Long cargaId, Carga cargaModificada) {
        Carga cargaExiste = cargaRepository.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("ProdutoCarga não encontrada para o ID: " + cargaId + "."));
        BeanUtils.copyProperties(cargaModificada, cargaExiste, "id");
        return cargaRepository.save(cargaExiste);
    }

    public InformacoesCarregamentoResponse obterInformacoesCarregamento() {
        List<Carga> cargas = cargaRepository.findBySituacaoCarga(SituacaoCarga.ATENDIDA);

        List<InformacoesCargaCondutorResponse> informacoes = cargas.stream()
                .map(this::criarInformacoesCargaCondutor)
                .collect(Collectors.toList());

        return new InformacoesCarregamentoResponse(informacoes);
    }

    private InformacoesCargaCondutorResponse criarInformacoesCargaCondutor(Carga carga) {
        InformacoesCargaCondutorResponse informacoes = new InformacoesCargaCondutorResponse();
        informacoes.setCarga(carga);

        Optional<Condutor> condutorOptional = condutorRepository.findById(carga.getCondutorId());
        condutorOptional.ifPresent(informacoes::setCondutor);

        return informacoes;
    }

    public Integer getGestorIdByUserId(Long userId) {
        return gestorRepository.findGestorIdByUserId(userId);
    }

    public void notificarMotorista(Long cargaId, Long condutorId){
        String numeroTelefone = obterNumeroTelefoneCondutor(condutorId);
        String mensagem = "Sua carga está pronta para ser retirada. Entre em contato para mais detalhes";
        enviarNotificacaoWhatsApp(numeroTelefone, mensagem);
    }
    private void enviarNotificacaoWhatsApp(String numeroTelefone, String mensagem){
        try{
            String numeroFormatado = numeroTelefone.replaceAll("[^0-9]","");
            String mensagemCodificada = URLEncoder.encode(mensagem, "UTF-8");

            String linkWhatsApp = "https://web.whatsapp.com/send?phone="+ numeroFormatado + "?text=" +
                    mensagemCodificada;


            System.out.println("Link do WhatsApp: " + linkWhatsApp);
            //Abre  o link no navegador

        } catch (UnsupportedEncodingException e){

            e.printStackTrace();
        }
    }

    private String obterNumeroTelefoneCondutor(Long condutorId){
        Condutor condutor = condutorRepository.findById(condutorId).orElseThrow(() ->
                new RuntimeException("Condutor não encontrado") );
        return condutor.getTelefone();
    }


}

