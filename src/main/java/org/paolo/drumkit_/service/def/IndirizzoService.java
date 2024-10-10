package org.paolo.drumkit_.service.def;


import org.paolo.drumkit_.model.Indirizzo;
import java.util.List;

public interface IndirizzoService extends GeneralService<Indirizzo> {
    List<Indirizzo> getIndirizziByCliente_Id(long id_utente);
}
