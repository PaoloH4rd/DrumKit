package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.RigaOrdine;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.paolo.drumkit_.service.def.RigaOrdineService;
import org.paolo.drumkit_.service.impl.OrdineServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RigaOrdineFacade {
    private final RigaOrdineService rigaOrdineService;
    private final ProdottoService prodottoService;
    private final OrdineServiceImpl ordineServiceImpl;

    public void aggiungiProdottoAlCarrello(Long prodottoId, int quantita) {
        if (prodottoService.getById(prodottoId).getQuantita() < quantita) {
            throw new IllegalArgumentException("Quantità non disponibile");
        }
        rigaOrdineService.aggiungiProdottoAlCarrello(prodottoService.getById(prodottoId), quantita);
    }
    //,eglio tornare una lista di righe ordine
    public List<RigaOrdine> getProdottiCarrello(Long idUtenteLoggato) {
        Ordine ordine =ordineServiceImpl.getByIdUtente(idUtenteLoggato);
        return rigaOrdineService.getAllRigheOrdine(ordine.getId());
    }
}
