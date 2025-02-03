package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.model.RigaOrdine;

import java.util.List;

public interface RigaOrdineService extends GeneralService<RigaOrdine> {

    void aggiungiProdottoAlCarrello(Prodotto prodotto, int quantita);
    List<RigaOrdine> getAllRigheOrdine(Long idUtenteLoggato);

    void delete(RigaOrdine r);

    List<RigaOrdine> getAllRigheOrdineByOrdine(Ordine ordine);
}
