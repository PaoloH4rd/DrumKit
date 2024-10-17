package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.repository.UtenteRepository;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UtenteServiceJPA implements UtenteService {

    private final UtenteRepository Urepo;

    @Override
    public Utente getByEmail(String email) {
        return Urepo.findByEmailAndIsDisattivatoIsFalse(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    //sql init properties

    @Override
    public boolean loginCheck(String email, String password) {
        //viene criptata la password usata
        //si controlla se esiste un utente con determinati username e password criptata
        Optional<Utente> u = Urepo.findByEmailAndPasswordAndIsDisattivatoIsFalse(email, password);
        //se è presebte lo ritorna
        return u.isPresent();
    }

    @Override
    public void add(Utente u) {
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
}
