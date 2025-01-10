package org.paolo.drumkit_.service.impl;


import jakarta.transaction.Transactional;
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

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

import static org.paolo.drumkit_.model.Ruolo.CLIENTE;


@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository Urepo;


    @Override
    public Utente getByEmail(String email) {
        return Urepo.findByEmailAndIsDisattivatoIsFalse(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean loginCheck(String email, String password) {
        //si controlla se esiste un utente con determinati username e password
        String encryptedPassword = DigestUtils.sha256Hex(password);
        Optional<Utente> u = Urepo.findByEmailAndPasswordAndIsDisattivatoIsFalse(email, encryptedPassword);
        System.out.println(u.isPresent());
        System.out.println(u.isPresent());
        System.out.println(u.isPresent());
        System.out.println(u.isPresent());
        System.out.println(u.isPresent());
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
        if (u.getId() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID utente non valido");
        }
        // Recupera l'utente dal database
        Utente vecchioUtente = Urepo.findById(u.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
//        System.out.println("password vecchia codificata "+ vecchioUtente.getPassword());

        //codifico la password nuova
        String encryptedPassword = DigestUtils.sha256Hex(u.getPassword());

        if (!encryptedPassword.equals(vecchioUtente.getPassword())) {
            //la password è diversa-> la cambio
            vecchioUtente.setPassword(encryptedPassword);
        }else {
            //la password non è stata cambiata
            throw new DatoNonValidoException("la password deve essere diversa da quella attuale");
        }
//        System.out.println("password nuova codificata: "+ encryptedPassword);
//        System.out.println(vecchioUtente.getEmail());
//        System.out.println(vecchioUtente.getPassword());
        Urepo.save(vecchioUtente);
    }

    @Override
    public Utente getById(long id) {
        return Urepo.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Utente> getAll() {
        return Urepo.findAllByIsDisattivatoIsFalse();
    }

    @Override
    public void setDisattivatoTrue(long id) {
        Utente utente = getById(id);
        utente.setDisattivato(true);
        Urepo.save(utente);
    }
    @Override
    public void creaCliente(String nome, String cognome, String email, String password, String passwordRipetuta) {
        if (!password.equals(passwordRipetuta))
            throw new DatoNonValidoException("Le password non coincidono");
        //si controlla che l'utente non esita già
        Utente user = new Utente();
        //si setta il suo username
        user.setNome(nome);
        user.setCognome(cognome);
        user.setEmail(email);
        //si cripta la password che ha utilizzato
        String encryptedPassword = DigestUtils.sha256Hex(password);
        //si setta la sua password criptata
        user.setPassword(encryptedPassword);
        user.setRuolo(CLIENTE);
        add(user);
    }
    public void creaAdmin(String nome, String cognome, String email, String password, String passwordSuperAdmin) {
        //si crea un nuovo utente
        if (!loginCheck(email, passwordSuperAdmin))throw new DatoNonValidoException("Password Super Admin non corretta");
        Utente user = new Utente();
        //si setta il suo username
        user.setNome(nome);
        user.setCognome(cognome);
        user.setEmail(email);
        //si cripta la password che ha utilizzato
        String encryptedPassword = DigestUtils.sha256Hex(password);
        //si setta la sua password criptata
        user.setPassword(encryptedPassword);
        //si salva nel database il nuovo utente
        user.setRuolo(Ruolo.ADMIN);
        add(user);
    }

}
