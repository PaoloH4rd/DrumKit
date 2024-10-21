package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.mapper.UtenteMapper;
import org.paolo.drumkit_.model.Ruolo;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtenteFacade {
    private final UtenteService utenteService;
    private final UtenteMapper mapper;

    //chiama metodo come l'api
    public Void registraCliente( String nome,String cognome, String email, String password) {
        Utente u = new Utente();
        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(email);
        u.setPassword(password);
        u.setRuolo(Ruolo.CLIENTE);
        utenteService.add(u);
        return null;
    }

    //controllare che l'utente con eemail e pasword esista
    //verra richiamato nel controller
    public boolean login(String email, String password) {
        //il controllo dei campi
        return utenteService.loginCheck(email, password);
    }

    public Void registraAdmin( String nome,String cognome, String email, String password) {
        Utente u = new Utente();
        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(email);
        u.setPassword(password);
        u.setRuolo(Ruolo.ADMIN);
        utenteService.add(u);
        return null;
    }



//    public UserDTO getProfile(UserModel userModel) {
//        //converte il user in DTO
//        return mapper.toUserDTO(userModel);
//    }
//
//    public void cambiaPassword(CambiaPasswordRequestDTO request, Utente u) {
//        System.out.println("Authenticated user: " + u.getEmail());
//        if(u.isDisattivato()) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
////        se la vecchia password dell'utente non è uguale alla password -> non sai la tua password
////        se la vecchia password non è la password dell'utente
//        if(!u.getPassword().equals(request.getVecchiaPassword())) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        }
//        if(!request.getNuovaPassword().equals(request.getNuovaPasswordRipetuta())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//        u.setPassword(request.getNuovaPassword());
//        utenteService.update(u);
//    }

    public void disattivaUtente(long id) {
        utenteService.setDisattivatoTrue(id);
    }
}