package org.paolo.drumkit_.service.impl;

import org.paolo.drumkit_.model.CartaDiCredito;
import org.paolo.drumkit_.repository.CartaDiCreditoRepository;
import org.paolo.drumkit_.service.def.CarteDiCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CartaDiCreditoServiceJPA implements CarteDiCreditoService {

    private final CartaDiCreditoRepository repo;

    //torna tutte le carte di un utente con il suo id
    @Override
    public List<CartaDiCredito> getByUtente_id(long id) {
        return repo.findAllByUtente_IdAndIsDisattivatoIsFalse(id);
    }

    @Override
    public void add(CartaDiCredito c) {
        repo.save(c);
    }

    @Override
    public void update(CartaDiCredito c) {
        CartaDiCredito carta = new CartaDiCredito();
        carta.setId(c.getId());
        carta.setNome(c.getNome());
        carta.setCognome(c.getCognome());
        carta.setDefault(c.isDefault());
        repo.save(carta);
    }

    @Override
    public CartaDiCredito getById(long id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<CartaDiCredito> getAll() {
        return repo.findAll();
    }

    @Override
    public void setDisattivatoTrue(long id) {
        CartaDiCredito carta = getById(id);
        carta.setDisattivato(true);
        repo.save(carta);
    }

}
