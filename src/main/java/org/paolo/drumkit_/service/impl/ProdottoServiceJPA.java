package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.repository.ProdottoRepository;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdottoServiceJPA implements ProdottoService {

    private final ProdottoRepository repo;

    @Override
    public void add(Prodotto prodotto) {
        repo.save(prodotto);
    }

    @Override
    public void update(Prodotto prodotto) {
        Prodotto p = new Prodotto();
        p.setId(prodotto.getId());
        p.setNome(prodotto.getNome());
        p.setFoto(prodotto.getFoto());
        p.setDescrizione(prodotto.getDescrizione());
        //TODO carìcare categoria
        p.setChats(prodotto.getChats());
        p.setPrezzo(prodotto.getPrezzo());
        p.setQuantita(prodotto.getQuantita());
        p.setRigaOrdine(prodotto.getRigaOrdine());
        p.setRecensioni(prodotto.getRecensioni());
        repo.save(p);
    }

    @Override
    public List<Prodotto> getAll() {
        return repo.findAllByIsDisattivatoIsFalse();
    }

    @Override
    public Prodotto getById(long id) {
        return repo.findByIdAndIsDisattivatoIsFalse(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void setDisattivatoTrue(long id) {
        Prodotto p = getById(id);
        p.setDisattivato(true);
        repo.save(p);
    }
}
