package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Indirizzo;
import org.paolo.drumkit_.repository.IndirizzoRepository;
import org.paolo.drumkit_.service.def.IndirizzoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndirizzoServiceImpl implements IndirizzoService {

    private final IndirizzoRepository repo;

    @Override
    public List<Indirizzo> getIndirizziByCliente_Id(long id_utente) {
        return repo.findAllByUtente_IdAndIsDisattivatoIsFalse(id_utente);
    }

    @Override
    public void add(Indirizzo indirizzo) {
        repo.save(indirizzo);
    }

    @Override
    public Indirizzo getById(long id) {
        return repo.findByIdAndIsDisattivatoIsFalse(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Indirizzo> getAll() {
        return repo.findAllByIsDefaultIsFalse();
    }

    @Override
    public void setIsDisattivatoTrue(long id) {
        Indirizzo i = getById(id);
        i.setDisattivato(true);

    }
}
