package org.paolo.drumkit_.service.def;


import org.paolo.drumkit_.model.Utente;
public interface UtenteService extends GeneralService<Utente> {
    Utente login(String email, String password);
    //registro l'utente non il dto
    //non sto registrando sto salvando nel db l'utente
    //il nome del metodo non rispecchia la query che viene fatta nella repo (findBy...Is disattivato)
    Utente getByEmail(String email);
}
