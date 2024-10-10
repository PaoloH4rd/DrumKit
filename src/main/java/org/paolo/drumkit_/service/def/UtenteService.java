package org.paolo.drumkit_.service.def;


import org.paolo.drumkit_.dto.request.RegistrazioneRequestDTO;
import org.paolo.drumkit_.model.Utente;
public interface UtenteService extends GeneralService<Utente> {
    Utente login(String email, String password);
    //registro l'utente non il dto
    //non sto registrando sto salvando nel db l'utente
}
