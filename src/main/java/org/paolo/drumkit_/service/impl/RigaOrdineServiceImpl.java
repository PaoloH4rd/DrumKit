package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.model.RigaOrdine;
import org.paolo.drumkit_.repository.RigaOrdineRepository;
import org.paolo.drumkit_.service.def.RigaOrdineService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RigaOrdineServiceImpl implements RigaOrdineService {

    private final RigaOrdineRepository repo;

    @Override
    public void add(RigaOrdine rigaOrdine) {
        repo.save(rigaOrdine);
    }

    @Override
    public void update(RigaOrdine rigaOrdine) {
        RigaOrdine old = new RigaOrdine();
        old.setId(rigaOrdine.getId());
        old.setOrdine(rigaOrdine.getOrdine());
        old.setQuantita(rigaOrdine.getQuantita());
        old.setProdotto(rigaOrdine.getProdotto());
        old.setPrezzoTot(rigaOrdine.getPrezzoTot());
        repo.save(old);
    }

    @Override
    public RigaOrdine getById(long id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<RigaOrdine> getAll() {
        return repo.findAll();
    }


    @Override
    public void setIsDisattivatoTrue(long id) {
        RigaOrdine r = getById(id);
        r.setDisattivato(true);
        repo.save(r);
    }

    @Override
    public void aggiungiProdottoAlCarrello(Prodotto prodotto, int quantita) {
        // crea una riga ordine con il prodotto scelto da mettere nel carrello
        // se il prodotto è già presente nel carrello, incrementa la quantità
        RigaOrdine rigaOrdine = new RigaOrdine();
        rigaOrdine.setProdotto(prodotto);
        rigaOrdine.setQuantita(quantita);
        rigaOrdine.setPrezzoTot(prodotto.getPrezzo() * quantita);
        repo.save(rigaOrdine);
    }
    public List<RigaOrdine> getAllRigheOrdine(Long idOrdine) {
//        return repo.findAllByOrdine(idOrdine);
        return null;
    }

}
