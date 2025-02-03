package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.StatoOrdine;

import java.util.List;

public interface OrdineService extends  GeneralService<Ordine>{
    Ordine getByIdUtente(long id);
    List<Ordine> getOrdineGiornalieri(long id_admin);
    List<Ordine> getByStato(StatoOrdine s);

    Ordine getOrdineAperto(Long idUtenteLoggato);

    List<Ordine> getAllOrdiniByUtenteId(long id);

    Ordine getUltimoOrdine(Long idLoggato);

}
