package org.paolo.drumkit_.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.utente.CambiaPasswordRequestDTO;
import org.paolo.drumkit_.dto.request.utente.LoginRequestDTO;
import org.paolo.drumkit_.dto.request.utente.RegistrazioneRequestDTO;
import org.paolo.drumkit_.dto.response.RegistrazioneResponseDTO;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.security.GestoreTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteFacade facade;
    private final GestoreTokenService tokenService;

    // /chipuo chiama l'api/cosa sto gestendo/ nome dell'api
    @PostMapping("/all/utente/registraCliente")
    public ResponseEntity<RegistrazioneResponseDTO> registraCliente (@Valid @RequestBody RegistrazioneRequestDTO request) {
        RegistrazioneResponseDTO registrazioneResponseDTO= facade.registraCliente(request);
        return ResponseEntity.ok(registrazioneResponseDTO);
    }
    @PostMapping("/all/utente/login")
    public ResponseEntity<Void> login (@Valid @RequestBody LoginRequestDTO request){
        Utente u = facade.login(request);
        //creo il token relativo all'utente che si è loggato
        String tokenCreato =tokenService.creaToken(u);
        //TODO che risposta è?
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization",tokenCreato)
                .build();
    }
    @PutMapping("/authorized/utente/cambiaPassword")
    public ResponseEntity<Void> cambiaPassword
            (@Valid @RequestBody CambiaPasswordRequestDTO request){
        facade.cambiaPassword(request);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/admin/utente/disattivaCliente/{id}")
    public ResponseEntity<Void> disattivaCliente(
            @PathVariable long id,@RequestHeader("User")String emailAdmin){
        facade.disattivaUtente(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/superAdmin/utente/disattivaAdmin/{id}")
    public ResponseEntity<Void> disattivaAdmin(
            @PathVariable long id,@RequestHeader("User")String emailSuperAdmin){
        facade.disattivaUtente(id);
        return ResponseEntity.ok().build();
    }
    //TODO getUtente
}