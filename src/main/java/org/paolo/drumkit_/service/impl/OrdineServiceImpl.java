package org.paolo.drumkit_.service.impl;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.StatoOrdine;
import org.paolo.drumkit_.repository.OrdineRepository;
import org.paolo.drumkit_.service.def.OrdineService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

    private final OrdineRepository repo;

    @Override
    public List<Ordine> getByIdUtente(long id) {
        return repo.findAllByUtente_IdAndIsDisattivatoIsFalse(id);
    }

    @Override
    public List<Ordine> getOrdineGiornalieri(long id_admin) {
        LocalDate date = LocalDate.now();
        return repo.findAllByDataConfermaAndIsDisattivatoIsFalse(date);
    }

    @Override
    public List<Ordine> getByStato(StatoOrdine s) {
        return repo.findAllByStatoOrdineAndIsDisattivatoIsFalse(s);
    }

    @Override
    public void add(Ordine ordine) {
        repo.save(ordine);
    }

    @Override
    public void update(Ordine ordine) {
        Ordine o = new Ordine();
        o.setId(ordine.getId());
        o.setRigaOrdine(ordine.getRigaOrdine());
        o.setDataConferma(ordine.getDataConferma());
        o.setUtente(ordine.getUtente());
        o.setStatoOrdine(ordine.getStatoOrdine());
        repo.save(o);
    }

    @Override
    public Ordine getById(long id) {
        return repo.findByIdAndIsDisattivatoIsFalse(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Ordine> getAll() {
        return repo.findAllByIsDisattivatoIsFalse();
    }


    @Override
    public void setIsDisattivatoTrue(long id) {
        Ordine o = getById(id);
        o.setDisattivato(true);
        repo.save(o);
    }
}
