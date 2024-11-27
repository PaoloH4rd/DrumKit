package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.repository.UtenteRepository;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.paolo.drumkit_.model.Ruolo.CLIENTE;


@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository Urepo;
    private final UtenteRepository utenteRepository;

    @Override
    public Utente getByEmail(String email) {
        return Urepo.findByEmailAndIsDisattivatoIsFalse(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void cambiaPassword(String password, String nuovaPassword) {
        // TOD0 : implementare il cambio password
    }

    @Override
    public boolean loginCheck(String email, String password) {
        //si controlla se esiste un utente con determinati username e password
        String encryptedPassword = DigestUtils.sha256Hex(password);
        Optional<Utente> u = Urepo.findByEmailAndPasswordAndIsDisattivatoIsFalse(email, encryptedPassword);
        //se è presente lo ritorna
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
    public void update(Utente u) {
        if (u.getId() < 1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Utente utente = new Utente();
        utente.setCognome(u.getCognome());
        utente.setNome(u.getNome());
        utente.setEmail(u.getEmail());
        utente.setPassword(u.getPassword());
        utente.setId(u.getId());
        utente.setRuolo(u.getRuolo());

        Urepo.save(utente);
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
    public boolean creaCliente(String nome, String cognome, String email, String password, String passwordRipetuta) {
    //si crea un nuovo utente
        if (!password.equals(passwordRipetuta)) throw new DatoNonValidoException("Le password non coincidono");
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
        user.setRuolo(CLIENTE);
        utenteRepository.save(user);
        return true;
    }
}
