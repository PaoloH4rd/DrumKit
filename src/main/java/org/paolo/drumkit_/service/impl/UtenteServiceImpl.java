package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.model.Ruolo;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.repository.UtenteRepository;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static org.paolo.drumkit_.model.Ruolo.CLIENTE;


@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository Urepo;


    @Override
    public Utente getByEmail(String email) {
        return Urepo.findByEmailAndIsDisattivatoIsFalse(email).orElseThrow(() -> new DatoNonValidoException("Utente non trovato"));
    }
    @Override
    public Utente getByEmailforChat(String email) {
        return Urepo.findByEmail(email).orElse(null);
    }


    @Override
    public boolean loginCheck(String email, String password) {
        //si controlla se esiste un utente con determinati utentename e password
        String encryptedPassword = DigestUtils.sha256Hex(password);
        Optional<Utente> u = Urepo.findByEmailAndPasswordAndIsDisattivatoIsFalse(email, encryptedPassword);
        return u.isPresent();
    }

    @Override
    public void add(Utente u) {
        //controllo se c'è gia un utente con la stessa email
        Optional<Utente> optionalUtente = Urepo.findByEmailAndIsDisattivatoIsFalse(u.getEmail());
        if (optionalUtente.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT);
        Urepo.save(u);
    }

    @Override
    public void cambiaPassword(Utente u) {
        Urepo.save(u);
    }


    @Override
    public Utente getById(long id) {
        return Urepo.findById(id).orElse(null);
    }

    //set disattivato true
    public void setIsDisattivatoTrue(long id) {
    }

    @Override
    public List<Utente> getAll() {
        return Urepo.findAllByIsDisattivatoIsFalse();
    }

    @Override
    public void setDisattivatoTrue(String email) {
        //controllo che l'utente esista e che sia un utente cliente
        Utente utente = getByEmail(email);
        if (utente.getRuolo() == Ruolo.ADMIN) throw new DatoNonValidoException("Non puoi disattivare un admin");
        //non puoi disattivare un utente gia disattivato
        if (utente.isDisattivato()) throw new DatoNonValidoException("Utente già disattivato");
        utente.setDisattivato(true);
        Urepo.save(utente);
    }
    @Override
    public void creaCliente(String nome, String cognome, String email, String password, String passwordRipetuta, String dataNascita) {
        //si controlla che l'utente non esita già
        Optional<Utente> optionalUtente = Urepo.findByEmail(email);
        if (optionalUtente.isPresent()) throw new DatoNonValidoException("Utente già esistente");
        Utente utente = new Utente();
        LocalDate data_Nascita;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adatta il formato al tuo caso
        data_Nascita = LocalDate.parse(dataNascita, formatter);

        //si setta il suo utentename e i campi
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        String encryptedPassword = DigestUtils.sha256Hex(password);
        utente.setPassword(encryptedPassword);
        utente.setRuolo(CLIENTE);
        utente.setDataNascita(data_Nascita);
        add(utente);
    }

    public void creaAdmin(String nome, String cognome, String email, String password, String passwordRipetuta, String dataNascita) {
        //controllo che l'utente non esista già
        Optional<Utente> optionalUtente = Urepo.findByEmail(email);
        if (optionalUtente.isPresent()) throw new DatoNonValidoException("Utente già esistente");
        Utente utente = new Utente();
        LocalDate data_Nascita;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adatta il formato al tuo caso
            data_Nascita = LocalDate.parse(dataNascita, formatter);
        } catch (DateTimeParseException e) {
            throw new DatoNonValidoException("Formato data non valido. Usa il formato AAAA-MM-GG.");
        }
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        //si cripta la password che ha utilizzato
        String encryptedPassword = DigestUtils.sha256Hex(password);
        //si setta la sua password criptata
        utente.setPassword(encryptedPassword);
        //si salva nel database il nuovo utente
        utente.setDataNascita(data_Nascita);
        utente.setRuolo(Ruolo.ADMIN);
        add(utente);
    }
    @Override
    public List<Utente> getAllActiveAdmins() {
        return Urepo.findAllByRuoloAndIsDisattivatoIsFalse(Ruolo.ADMIN);
    }

    @Override
    public String getNomeByEmail(String email) {
        return Urepo.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getNome();
    }

    @Override
    public List<Utente> getUtentiBloccati() {
        return Urepo.findAllByIsDisattivatoIsTrue();
    }

    @Override
    public void setDisattivatoTrueSAdmin(String email) {
        Utente utente = getByEmail(email);
        if (utente.getRuolo() == Ruolo.CLIENTE) throw new DatoNonValidoException("Non puoi disattivare un cliente");
        if (utente.isDisattivato()) throw new DatoNonValidoException("Utente già disattivato");
        utente.setDisattivato(true);
        Urepo.save(utente);
    }

}
