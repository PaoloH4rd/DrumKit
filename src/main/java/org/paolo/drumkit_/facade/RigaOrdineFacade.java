package org.paolo.drumkit_.facade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.model.*;
import org.paolo.drumkit_.service.def.OrdineService;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.paolo.drumkit_.service.def.RigaOrdineService;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RigaOrdineFacade {
    private final RigaOrdineService rigaOrdineService;
    private final ProdottoService prodottoService;
    private final OrdineService ordineService;
    private final UtenteService utenteService;

    public void aggiungiProdottoAlCarrello(Long prodottoId, int quantita, Long idUtenteLoggato) {
        Ordine o = ordineService.getOrdineAperto(idUtenteLoggato);
        if(o==null){
            o = new Ordine();
            Utente u = utenteService.getById(idUtenteLoggato);
            o.setUtente(u);
            o.setStatoOrdine(StatoOrdine.IN_ATTESA);
            ordineService.add(o);
        }
        if (prodottoService.getById(prodottoId).getQuantita() < quantita) {
            throw new IllegalArgumentException("Quantità non disponibile");
        }
        if (o.getRigaOrdine()==null){
            o.setRigaOrdine(new ArrayList<>());
        }
        RigaOrdine r = null;
        for (RigaOrdine riga : o.getRigaOrdine()) {
            if (riga.getProdotto().getId() == prodottoId)  {
                r = riga;
                break;
            }
        }
        if (r == null) {
            r = new RigaOrdine();
            r.setProdotto(prodottoService.getById(prodottoId));
            r.setQuantita(quantita);
            r.setOrdine(o);
            o.getRigaOrdine().add(r);
        } else {
            r.setQuantita(r.getQuantita() + quantita);
        }
        rigaOrdineService.add(r);
    }
    //,eglio tornare una lista di righe ordine
    public List<RigaOrdine> getProdottiCarrello(Long idUtenteLoggato) {
        Ordine ordine = ordineService.getOrdineAperto(idUtenteLoggato);
        if(ordine==null){
            return new ArrayList<>();
        }
        return ordine.getRigaOrdine()==null?new ArrayList<>():ordine.getRigaOrdine();
    }

    @Transactional(rollbackOn = DatoNonValidoException.class)
    public Ordine confermaCarrello(Long idUtenteLoggato) {
        Ordine ordine = ordineService.getOrdineAperto(idUtenteLoggato);
        if(ordine==null){
            throw new DatoNonValidoException("Non ci sono prodotti nel carrello");
        }
        for (RigaOrdine riga : ordine.getRigaOrdine()) {
            // Ottieni la quantità disponibile aggiornata dal database
            Prodotto prodotto = prodottoService.getById(riga.getProdotto().getId());
            // Verifica se la quantità disponibile è sufficiente
            if (prodotto.getQuantita() < riga.getQuantita()) {
                throw new DatoNonValidoException("Quantità non disponibile per il prodotto: " + prodotto.getNome());
            }
        }
        // Aggiorna la quantità disponibile dei prodotti e le righe ordine
        ordine.setDataConferma(LocalDate.now());
        if(ordine.getRigaOrdine()==null) throw new DatoNonValidoException("Non ci sono prodotti nel carrello");
        for (RigaOrdine r : ordine.getRigaOrdine()) {
            if (r.getProdotto().getQuantita() < r.getQuantita()) {
                throw new DatoNonValidoException("Quantità non disponibile");
            }else{
                r.setPrezzoTot(r.getProdotto().getPrezzo() * r.getQuantita());
            }
            prodottoService.add(r.getProdotto());
            r.getProdotto().setQuantita(r.getProdotto().getQuantita()-r.getQuantita());

            if (r.getProdotto().getQuantita() == 0) {
                r.getProdotto().setStato(StatoProdotto.VENDUTO);
                rigaOrdineService.add(r);
            }
        }
        //stato ordine
        ordine.setStatoOrdine(StatoOrdine.CONFERMATO);
        ordineService.add(ordine);

        return ordine;
    }

    public double getTotaleCarrello(long id) {
        Ordine ordine = ordineService.getOrdineAperto(id);
        if(ordine==null){
            return 0;
        }
        // altrimenti ritorno la somma dei prezzi totali delle righe ordine
        return ordine.getRigaOrdine()==null?0:ordine.getRigaOrdine().stream().mapToDouble(RigaOrdine::getPrezzoTot).sum();
    }


    public Ordine getOrdineAperto(long idLoggato) {
        return ordineService.getOrdineAperto(idLoggato);
    }

    public void cambiaQuantita(Long idProdotto, int quantita, long id) {
        //scala la quantità di un prodotto nel carrello
        Ordine ordine = ordineService.getOrdineAperto(id);
        if(ordine==null){
            return;
        }
        for (RigaOrdine riga : ordine.getRigaOrdine()) {
            if (riga.getProdotto().getId() == idProdotto) {
                riga.setQuantita(quantita);
                riga.setPrezzoTot(riga.getProdotto().getPrezzo() * quantita);
                rigaOrdineService.add(riga);
                break;
            }
        }
    }

    public void rimuoviProdotto(Long idProdotto, long idLoggato) {
        //rimuovi un prodotto dal carrello
        Ordine ordine = ordineService.getOrdineAperto(idLoggato);
        if(ordine==null){
            return;
        }
        RigaOrdine r = null;
        for (RigaOrdine riga : ordine.getRigaOrdine()) {
            if (riga.getProdotto().getId() == idProdotto) {
                r = riga;
                break;
            }
        }
        if (r != null) {
            ordine.getRigaOrdine().remove(r);
            rigaOrdineService.delete(r);
        }
    }

}
