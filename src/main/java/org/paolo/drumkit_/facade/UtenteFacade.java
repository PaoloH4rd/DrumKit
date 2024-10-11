package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.utente.CambiaPasswordRequestDTO;
import org.paolo.drumkit_.dto.request.utente.LoginRequestDTO;
import org.paolo.drumkit_.dto.request.utente.RegistrazioneRequestDTO;
import org.paolo.drumkit_.dto.response.RegistrazioneResponseDTO;
import org.paolo.drumkit_.mapper.UtenteMapper;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UtenteFacade {
    private final UtenteService utenteService;
    private final UtenteMapper mapper;

    //chiama metodo come l'api
    public RegistrazioneResponseDTO registraCliente(RegistrazioneRequestDTO request){
        if (!request.getPassword().equals(request.getPasswordRipetuta())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Utente u = mapper.fromRegistrazioneRequestDTO(request);
        utenteService.add(u);

        return mapper.toRegistrazioneResponseDTO(u);
    }
    //controllare che l'utente con eemail e pasword esista
    //verra richiamato nel controller
    public Utente login(LoginRequestDTO request) {
        Utente u=utenteService.login(request.getEmail(), request.getPassword());
        return u;
    }
    public void cambiaPassword(CambiaPasswordRequestDTO request) {
        Utente u=utenteService.getById(request.getId());
        if(u.isDisattivato()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(!u.getPassword().equals(request.getVecchiaPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if(!request.getNuovaPassword().equals(request.getNuovaPasswordRipetuta())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        u.setPassword(request.getNuovaPassword());
        utenteService.update(u);
    }
    public void disattivaUtente(long id) {
        utenteService.setDisattivatoTrue(id);
    }


}
