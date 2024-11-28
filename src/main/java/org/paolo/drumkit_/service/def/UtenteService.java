package org.paolo.drumkit_.service.def;


import org.paolo.drumkit_.model.Utente;
public interface UtenteService extends GeneralService<Utente> {
    boolean loginCheck(String email, String password);
    //registro l'utente non il dto
    //non sto registrando sto salvando nel db l'utente
    //il nome del metodo non rispecchia la query che viene fatta nella repo (findBy...Is disattivato)
    Utente getByEmail(String email);
    void cambiaPassword(String password, String nuovaPassword);
    void creaCliente(String nome,String cognome,String email, String password,String passwordRipetuta);
    void creaAdmin(String nome,String cognome,String email, String password,String passwordSuperAdmin);
}