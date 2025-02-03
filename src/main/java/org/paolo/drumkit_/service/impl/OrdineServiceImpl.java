package org.paolo.drumkit_.service.impl;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.StatoOrdine;
import org.paolo.drumkit_.repository.OrdineRepository;
import org.paolo.drumkit_.service.def.OrdineService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Ordine getByIdUtente(long id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
    //implementa
    @Override
    public List<Ordine> getAllOrdiniByUtenteId(long id) {
        return repo.findAllByUtente_IdAndIsDisattivatoIsFalse(id);
    }

    //get ultimo ordine
//    public Ordine getUltimoOrdine(Long idUtente) {
//        Pageable pageable = PageRequest.of(0, 1); // Prima pagina, un solo risultato
//        return repo.findUltimoOrdineEseguito(idUtente, pageable)
//                .stream()
//                .findFirst()
//                .orElse(null);
//    }

    @Override
    public Ordine getUltimoOrdine(Long idLoggato) {
        Pageable pageable = PageRequest.of(0, 1); // Prima pagina, un solo risultato
        List<Ordine> ordini = repo.findFirstByUtente_IdAndStatoOrdine(idLoggato, StatoOrdine.CONFERMATO, pageable);
        Ordine primoOrdine = ordini.isEmpty() ? null : ordini.get(0);
        return primoOrdine;

    }
}
