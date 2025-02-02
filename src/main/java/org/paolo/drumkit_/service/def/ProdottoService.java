package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.model.Utente;

import java.util.List;

public interface ProdottoService extends GeneralService<Prodotto> {
    void creaProdotto(String nome, String descrizione, double prezzo, int quantita, Utente venditore,String immagine);
    Utente getProprietario(Long id);
    void setStatoProdottoRifiutato(Long idProdotto);
    List<Prodotto> getAllProdottiDaApprovare();
    List<Prodotto> getAllProdottiApprovatiNonDiUtenteLoggato(Long idUtente);
     void setStatoProdottoApprovato(Long idProdotto);
     List<Prodotto> getAllProdottiApprovareById(Long idVenditore);
    List<Prodotto> getAllApprovatiByVenditoreEmail(String email);
    }
