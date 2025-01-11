package org.paolo.drumkit_.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.paolo.drumkit_.exception.DatoNonValidoException;
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
        return Urepo.findByEmailAndIsDisattivatoIsFalse(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
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
    @Transactional
    public void update(Utente u) {
        if (u.getId() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID utente non valido");
        }
        // Recupera l'utente dal database
        Utente existingUtente = Urepo.findById(u.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        //aggiorna i campi
        existingUtente.setNome(u.getNome());
        existingUtente.setCognome(u.getCognome());
        existingUtente.setEmail(u.getEmail());
        existingUtente.setPassword(u.getPassword());
        existingUtente.setRuolo(u.getRuolo());
        Urepo.save(u);
    }

    @Override
    public void cambiaPassword(Utente u) {
        Urepo.save(u);
    }


    @Override
    public Utente getById(long id) {
        return Urepo.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void setDisattivatoTrue(long id) {

    }

    @Override
    public List<Utente> getAll() {
        return Urepo.findAllByIsDisattivatoIsFalse();
    }

    @Override
    public void setDisattivatoTrue(String email) {
        Utente utente = getByEmail(email);
        utente.setDisattivato(true);
        Urepo.save(utente);
    }
    @Override
    public void creaCliente(String nome, String cognome, String email, String password, String passwordRipetuta, String dataNascita) {
        //si controlla che l'utente non esita già
        Utente utente = new Utente();
        LocalDate data_Nascita;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adatta il formato al tuo caso
            data_Nascita = LocalDate.parse(dataNascita, formatter);
        } catch (DateTimeParseException e) {
            throw new DatoNonValidoException("Formato data non valido. Usa il formato AAAA-MM-GG.");
        }
        //si setta il suo utentename
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        String encryptedPassword = DigestUtils.sha256Hex(password);
        utente.setPassword(encryptedPassword);
        utente.setRuolo(CLIENTE);
        utente.setDataNascita(data_Nascita);
        add(utente);
    }
//    public void creaAdmin(String nome, String cognome, String email, String password, String passwordSuperAdmin,String dataNascita) {
//        //si crea un nuovo utente
//        if (!loginCheck(email, passwordSuperAdmin))throw new DatoNonValidoException("Password Super Admin non corretta");
//        Utente utente = new Utente();
//        LocalDate data_Nascita;
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adatta il formato al tuo caso
//            data_Nascita = LocalDate.parse(dataNascita, formatter);
//        } catch (DateTimeParseException e) {
//            throw new DatoNonValidoException("Formato data non valido. Usa il formato AAAA-MM-GG.");
//        }
//        //si setta il suo utentename
//        utente.setNome(nome);
//        utente.setCognome(cognome);
//        utente.setEmail(email);
//        //si cripta la password che ha utilizzato
//        String encryptedPassword = DigestUtils.sha256Hex(password);
//        //si setta la sua password criptata
//        utente.setPassword(encryptedPassword);
//        //si salva nel database il nuovo utente
//        utente.setRuolo(Ruolo.ADMIN);
//        add(utente);
//    }

}
