package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.model.RigaOrdine;

import java.util.List;

public interface RigaOrdineService  {

    void delete(RigaOrdine r);

    List<RigaOrdine> getAllRigheOrdineByOrdine(Ordine ordine);

    void add(RigaOrdine r);
}
