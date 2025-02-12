package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.paolo.drumkit_.dto.response.AdminDisattivaSelectResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.exception.UtenteDisattivatoException;
import org.paolo.drumkit_.mapper.UtenteMapper;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtenteFacade {
    private final UtenteService utenteService;
    private final UtenteMapper mapper;

    //chiama metodo come l'api
    public void registraCliente(String nome, String cognome, String email, String password, String passwordRipetuta, String dataNascita) {
        if (!password.equals(passwordRipetuta))
            throw new DatoNonValidoException("Le password non coincidono");
         utenteService.creaCliente(nome,cognome,email,password,passwordRipetuta,dataNascita);
    }

    //controllare che l'utente con eemail e pasword esista
    //verra richiamato nel controller
    public boolean login(String email, String password) {
        return utenteService.loginCheck(email, password);
    }

    public UtenteResponseDTO getProfile(Utente utente) {
        //converte l'utente in DTO
        return mapper.toUtenteDTO(utente);
    }


    public void cambiaPassword(String  email,String vecchiaPassword, String nuovaPassword, String nuovaPasswordRipetuta) {
        Utente u = utenteService.getByEmail(email);
        if(u.isDisattivato()) {
            throw new UtenteDisattivatoException("Utente disattivato");
        }
        //se la password vecchia non corrisponde a quella dell'utente
        if(!u.getPassword().equals(DigestUtils.sha256Hex(vecchiaPassword))) {
            throw new DatoNonValidoException("Password errata");
        }
        //se la password nuova e la password ripetuta non corrispondono
        if(!nuovaPassword.equals(nuovaPasswordRipetuta)) {
            throw new DatoNonValidoException("Password non corrispondenti");
        }
        //se la password nuova è uguale a quella vecchia
        if (u.getPassword().equals(DigestUtils.sha256Hex(nuovaPassword))) {
            throw new DatoNonValidoException("La password deve essere differente da quella attuale");
        }
        u.setPassword(DigestUtils.sha256Hex(nuovaPassword));
        utenteService.cambiaPassword(u);
    }
    public List<AdminDisattivaSelectResponseDTO> getAllActiveAdmins() {
        List<Utente> admins = utenteService.getAllActiveAdmins();
        return admins.stream().map(mapper::toAdminDTO).collect(Collectors.toList());
    }


    public void disattivaUtente(String email ) {
        utenteService.setDisattivatoTrue(email);
    }
    //disattiva un admin
    public void disattivaAdmin(Long adminId) {
        Utente u = utenteService.getById(adminId);
        utenteService.setDisattivatoTrueSAdmin(u.getEmail());

    }
    public void registraAdmin(String nome, String cognome, String email, String password, String passwordRipetuta,String dataNascita) {
        if (!password.equals(passwordRipetuta))
            throw new DatoNonValidoException("Le password non coincidono");
        utenteService.creaAdmin(nome,cognome,email,password,passwordRipetuta,dataNascita);
    }

    //get nome by email
    public String getNomeByEmail(String email) {
        return utenteService.getNomeByEmail(email);
    }
    //get utenti bloccati
    public List<UtenteResponseDTO> getUtentiBloccati() {
        List<Utente> utenti = utenteService.getUtentiBloccati();
        return utenti.stream().map(mapper::toUtenteDTO).collect(Collectors.toList());
    }

    // get by email
    public Utente getByEmail(String email) {
        return utenteService.getByEmail(email);
    }
}