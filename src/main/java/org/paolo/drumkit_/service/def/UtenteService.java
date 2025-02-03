package org.paolo.drumkit_.service.def;


import org.paolo.drumkit_.model.Utente;

import java.util.List;

public interface UtenteService extends GeneralService<Utente> {
    boolean loginCheck(String email, String password);
    //registro l'utente non il dto
    //non sto registrando sto salvando nel db l'utente
    //il nome del metodo non rispecchia la query che viene fatta nella repo (findBy...Is disattivato)
    Utente getByEmail(String email);
    void cambiaPassword(Utente u);
    void creaCliente(String nome, String cognome, String email, String password, String passwordRipetuta, String dataNascita);
    void creaAdmin(String nome, String cognome, String email, String password, String passwordRipetuta, String dataNascita);
    void setDisattivatoTrue(String email);
    List<Utente> getAllActiveAdmins();
    Utente getByEmailforChat(String email);
    String getNomeByEmail(String email);
    //get tutti utenti bloccati
    List<Utente> getUtentiBloccati();
}