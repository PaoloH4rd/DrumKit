package org.paolo.drumkit_.service.impl;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.repository.OrdineRepository;
import org.paolo.drumkit_.service.def.OrdineService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

    private final OrdineRepository repo;


    @Override
    public Ordine getOrdineAperto(Long idUtenteLoggato) {

        return repo.findByUtente_IdAndDataConfermaIsNull(idUtenteLoggato).orElse(null);
    }

    @Override
    public void add(Ordine ordine) {
        repo.save(ordine);
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
