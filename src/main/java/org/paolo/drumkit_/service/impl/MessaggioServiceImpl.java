package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Messaggio;
import org.paolo.drumkit_.repository.MessaggioRepository;
import org.paolo.drumkit_.service.def.MessaggioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MessaggioServiceImpl implements MessaggioService {

    private final MessaggioRepository repo;

    @Override
    public void add(Messaggio messaggio) {
        repo.save(messaggio);
    }

    @Override
    public void update(Messaggio messaggio) {
        Messaggio m = new Messaggio();
        m.setId(messaggio.getId());
        m.setData(messaggio.getData());
        m.setChat(messaggio.getChat());
        m.setCliente(messaggio.isCliente());
        m.setTesto(messaggio.getTesto());
        repo.save(m);
    }
    @Override
    public Messaggio getById(long id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Messaggio> getAll() {
        return repo.findAll();
    }


    @Override
    public void setIsDisattivatoTrue(long id) {
        Messaggio m = getById(id);
        m.setDisattivato(true);
        repo.save(m);
    }
}
