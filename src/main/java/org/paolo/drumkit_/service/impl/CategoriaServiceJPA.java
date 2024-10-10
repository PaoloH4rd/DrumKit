package org.paolo.drumkit_.service.impl;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Categoria;
import org.paolo.drumkit_.repository.CategoriaRepository;
import org.paolo.drumkit_.service.def.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceJPA implements CategoriaService {

    private final CategoriaRepository repo;

    @Override
    public void add(Categoria categoria) {
        repo.save(categoria);
    }

    @Override
    public void update(Categoria categoria) {
        Categoria c = new Categoria();
        c.setId(categoria.getId());
        c.setNome(categoria.getNome());
        c.setProdotti(categoria.getProdotti());
        repo.save(c);
    }

    @Override
    public Categoria getById(long id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Categoria> getAll() {
        return repo.findAll();
    }

    @Override
    public void setDisattivatoTrue(long id) {
        Categoria c = getById(id);
        c.setDisattivato(true);
        repo.save(c);
    }
}