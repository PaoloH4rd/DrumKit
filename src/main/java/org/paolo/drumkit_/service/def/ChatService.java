package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Utente;

import java.util.List;

public interface ChatService extends GeneralService<Chat> {
    List<Chat> getByCliente_IdAndIsChiusaIsfalse(Utente cliente);
}
