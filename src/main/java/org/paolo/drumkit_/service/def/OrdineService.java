package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.StatoOrdine;

import java.util.List;

public interface OrdineService extends  GeneralService<Ordine>{
    Ordine getOrdineAperto(Long idUtenteLoggato);

}
