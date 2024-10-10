package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.CartaDiCredito;

import java.util.List;

public interface CarteDiCreditoService extends GeneralService<CartaDiCredito>{
    List<CartaDiCredito> getByUtente_id(long idUtente);
}
