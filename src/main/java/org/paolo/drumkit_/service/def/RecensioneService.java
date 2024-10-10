package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Recensione;
import java.util.List;

public interface RecensioneService extends GeneralService<Recensione> {
    List<Recensione> getAllByIdUtente(long id_utente);
    List<Recensione> getAllByIdProdotto(long id_prodotto);
}
