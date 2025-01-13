package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.model.StatoProdotto;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.repository.ProdottoRepository;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdottoServiceImpl implements ProdottoService {

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
        p.setDescrizione(prodotto.getDescrizione());
        p.setChats(prodotto.getChats());
        p.setPrezzo(prodotto.getPrezzo());
        p.setQuantita(prodotto.getQuantita());
        p.setRigaOrdine(prodotto.getRigaOrdine());
        repo.save(p);
    }

    @Override
    public Prodotto getById(long id) {
        return repo.findByIdAndIsDisattivatoIsFalse(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Prodotto> getAll() {
        return repo.findAllByIsDisattivatoIsFalse();
    }

    @Override
    public void setIsDisattivatoTrue(long id) {
        Prodotto p = getById(id);
        p.setDisattivato(true);
        repo.save(p);
    }
    // Implementazione del metodo per recuperare i prodotti degli altri clienti
    @Override
    public List<Prodotto> getAllProdottiAltriClienti(Long idProprietario) {
        return repo.findAllByProprietarioIdIsNot(idProprietario);
    }

    @Override
    public void creaProdotto(String nome, String descrizione, double prezzo, int quantita) {
        Prodotto p = new Prodotto();
        p.setNome(nome);
        p.setDescrizione(descrizione);
        p.setPrezzo(prezzo);
        p.setQuantita(quantita);
        repo.save(p);
    }
    public Utente getProprietario(Long idProdotto) {
        // Recupera il prodotto o lancia un'eccezione se non trovato
        Prodotto prodotto = repo.findById(idProdotto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodotto non trovato"));

        // Restituisci il proprietario del prodotto
        return prodotto.getProprietario();
    }

    @Override
    public void setStatoProdotto(Long idProdotto) {
        Prodotto p = getById(idProdotto);
        p.setStato(StatoProdotto.RIFIUTATO);
        repo.save(p);

    }
}
