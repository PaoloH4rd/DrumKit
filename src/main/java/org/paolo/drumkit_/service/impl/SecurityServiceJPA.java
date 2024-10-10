package org.paolo.drumkit_.service.impl;


import org.paolo.drumkit_.model.Ruolo;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.repository.UtenteRepository;
import org.paolo.drumkit_.service.def.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
@RequiredArgsConstructor
public class SecurityServiceJPA implements SecurityService {

    private final UtenteRepository repo;


    @Override
    public void isAdmin(String email) {
        checkByEmail(email , Ruolo.ADMIN);
    }

    @Override
    public void isAdmin(long id) {
       checkById(id ,Ruolo.ADMIN);
    }

    @Override
    public void isCliente(String email) {
        checkByEmail(email , Ruolo.CLIENTE);
    }

    @Override
    public void isCliente(long id) {
        checkById(id , Ruolo.CLIENTE);
    }

    @Override
    public void isSuperAdmin(String email) {
        checkByEmail(email , Ruolo.SUPER_ADMIN);
    }

    @Override
    public void isSuperAdmin(long id) {
        checkById(id , Ruolo.SUPER_ADMIN);
    }


    // Prototipazione , abbiamo creato dei metodi per poi usarli negli altri , in modo che se dobbiamo fare dei cambiamenti
    // ci basta cambiare quello che c'è qua sotto e poi non dobbiamo più cambiare quello sopra


    private void checkDiritti(Utente u , Ruolo ruolo) {
        if(u.isDisattivato())throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        if(u.getRuolo()!=ruolo) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    private void checkByEmail(String email , Ruolo ruolo){
        Utente u = repo.findByEmailAndIsDisattivatoIsFalse(email).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        checkDiritti(u , ruolo);
    }

    private void checkById(long id , Ruolo ruolo){
        Utente u = repo.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        checkDiritti(u , ruolo);
    }
}
