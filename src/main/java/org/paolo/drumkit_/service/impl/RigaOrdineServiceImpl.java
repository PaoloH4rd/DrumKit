package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.RigaOrdine;
import org.paolo.drumkit_.repository.RigaOrdineRepository;
import org.paolo.drumkit_.service.def.RigaOrdineService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RigaOrdineServiceImpl implements RigaOrdineService {

    private final RigaOrdineRepository repo;

    //delete riga ordine
    @Override
    public void delete(RigaOrdine r) {
        repo.delete(r);
    }

    @Override
    public List<RigaOrdine> getAllRigheOrdineByOrdine(Ordine ordine) {
        return repo.findAllByOrdine(ordine);
    }

    //add riga ordine
    @Override
    public void add(RigaOrdine r) {
        repo.save(r);
    }

}
