package org.paolo.drumkit_.service.impl;

import org.paolo.drumkit_.model.Foto;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.repository.FotoRepository;
import org.paolo.drumkit_.service.def.FotoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FotoServiceJPA implements FotoService {

    private final FotoRepository repo;

    @Override
    public void add(Foto foto) {
        repo.save(foto);
    }

    @Override
    public void update(Foto foto) {
        Foto f = new Foto();
        f.setId(foto.getId());
        f.setLink(foto.getLink());
        f.setProdotto(foto.getProdotto());
        repo.save(f);
    }

    @Override
    public Foto getById(long id) {
        return repo.findByIdAndIsDisattivatoIsFalse(id);
    }

    @Override
    public List<Foto> getAll() {
        return repo.findAllByIsDisattivatoIsFalse();
    }

    @Override
    public void setDisattivatoTrue(long id) {
        Foto f = getById(id);
        f.setDisattivato(true);
    }
}
