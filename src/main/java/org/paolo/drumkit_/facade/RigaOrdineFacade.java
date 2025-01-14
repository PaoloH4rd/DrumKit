package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.paolo.drumkit_.service.def.RigaOrdineService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RigaOrdineFacade {
    private final RigaOrdineService rigaOrdineService;
    private final ProdottoService prodottoService;

    public void aggiungiProdottoAlCarrello(Long prodottoId, int quantita) {
        if (prodottoService.getById(prodottoId).getQuantita() < quantita) {
            throw new IllegalArgumentException("Quantità non disponibile");
        }
        rigaOrdineService.aggiungiProdottoAlCarrello(prodottoService.getById(prodottoId), quantita);
    }
    //,eglio tornare una lista di righe ordine
    public void getProdottiCarrello(Long idUtenteLoggato) {
        return rigaOrdineService.getAllRigheOrdine(idUtenteLoggato);
    }
}
