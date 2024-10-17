package org.paolo.drumkit_.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.utente.CambiaPasswordRequestDTO;
import org.paolo.drumkit_.dto.request.utente.LoginRequestDTO;
import org.paolo.drumkit_.dto.request.utente.RegistrazioneAdminRequestDTO;
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
    
    @GetMapping("/home")
    public String defaultPage() {
        return "register_login_logout_profile/register.html";
    }
    //redirect from registrazione a login page
    @GetMapping("/login")
    public String login() {
        return "register_login_logout_profile/login.html";
    }
    //redirect alla dashboard
    @GetMapping("/authorized/utente/dashboard")
    public String dashboard() {
        //put token in the header
        return "dashboard/dashboard.html";
    }
    //redirect a login failed
    @GetMapping("/login_failed")
    public String loginFailed() {
        return "register_login_logout_profile/login_failed.html";
    }
    //redirect a logout
    @GetMapping("/logout")
    public String logout() {
        return "register_login_logout_profile/logout.html";
    }
    //redirect a registrazione fallita
    @GetMapping("/registration_failed")
    public String registrazioneFallita() {
        return "register_login_logout_profile/registration_failed.html";
    }
    //logout method


    // /chi puo chiama l'api/cosa sto gestendo/ nome dell'api
    @PostMapping("/all/utente/registraCliente")
    public ResponseEntity<RegistrazioneResponseDTO> registraCliente (@Valid @RequestBody RegistrazioneRequestDTO request) {
        RegistrazioneResponseDTO registrazioneResponseDTO= facade.registraCliente(request);
        return ResponseEntity.ok(registrazioneResponseDTO);
    }
    @PostMapping("/superAdmin/utente/registraAdmin")
    public ResponseEntity<Void> registraAdmin (@Valid @RequestBody RegistrazioneAdminRequestDTO request) {
        facade.registraAdmin(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/all/utente/login")
    public ResponseEntity<Void> login (@Valid @RequestBody LoginRequestDTO request){
        Utente u = facade.login(request);
        //creo il token relativo all'utente che si è loggato
        String tokenCreato =tokenService.creaToken(u);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization",tokenCreato)
                .build();
    }
    @PutMapping("/authorized/utente/cambiaPassword")
    //quando uso lo usernamePasswordAuthenticationToken (UPAT) devo passare il token nell'header
    public ResponseEntity<Void> cambiaPassword
            (@Valid @RequestBody CambiaPasswordRequestDTO request, UsernamePasswordAuthenticationToken upat){
        Utente u = (Utente) upat.getPrincipal();
        facade.cambiaPassword(request, u);

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