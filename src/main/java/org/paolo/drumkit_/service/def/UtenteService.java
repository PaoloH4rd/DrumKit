package org.paolo.drumkit_.service.def;


import org.paolo.drumkit_.model.Utente;
public interface UtenteService extends GeneralService<Utente> {
    Utente login(String email, String password);
}
