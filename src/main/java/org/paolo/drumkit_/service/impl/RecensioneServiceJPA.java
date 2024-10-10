package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Recensione;
import org.paolo.drumkit_.repository.RecensioneRepository;
import org.paolo.drumkit_.service.def.RecensioneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RecensioneServiceJPA implements RecensioneService {

    private final RecensioneRepository repo;

    @Override
    public List<Recensione> getAllByIdUtente(long id_utente) {
        return repo.findAllByUtente_IdAndIsDisattivatoIsFalse(id_utente);
    }

    @Override
    public List<Recensione> getAllByIdProdotto(long id_prodotto) {
        return repo.findAllByProdotto_IdAndIsDisattivatoIsFalse(id_prodotto);
    }

    @Override
    public void add(Recensione recensione) {
        repo.save(recensione);
    }

    @Override
    public void update(Recensione recensione) {
        Recensione rec = new Recensione();
        rec.setId(recensione.getId());
        rec.setVoto(recensione.getVoto());
        rec.setProdotto(recensione.getProdotto());
        rec.setUtente(recensione.getUtente());
        rec.setTitolo(recensione.getTitolo());
        rec.setTesto(recensione.getTesto());
        repo.save(rec);
    }

    @Override
    public List<Recensione> getAll() {
        return repo.findAll();
    }

    @Override
    public Recensione getById(long id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void setDisattivatoTrue(long id) {
        Recensione r = getById(id);
        r.setDisattivato(true);
        repo.save(r);
    }
}
