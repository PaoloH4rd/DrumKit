package org.paolo.drumkit_.controller;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteFacade facade;

    // /chi puo chiama l'api/cosa sto gestendo/ nome dell'api
    @PostMapping("/all/utente/registraCliente")
    public String registraCliente ( @RequestParam("nome")String nome,
                                    @RequestParam("cognome") String cognome,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password,
                                    @RequestParam("passwordRipetuta")String passwordRipetuta, HttpSession session) {
        //si controlla che l'utente non esita già
        if (!facade.login(email, password) || !password.equals(passwordRipetuta)){
            System.out.println("registrazione sta avvenendo");
            //viene creato un nuovo account
            facade.registraCliente(nome,cognome,email,password);
            //viene settato lo username dell'utente nella sessione
            System.out.println("registrazione avvenuta con successo");
            return "home_page";
        }
        else {
            return "register_login_logout_profile/registration_failed";
        }
    }
    @PostMapping(path = "/all/utente/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session){
        //viene fatto il login
        if (facade.login(email, password)){
            //viene settato lo username dell'utente nella sessione
            session.setAttribute("email", email);
            return "dashboard/dashboard";
        }
        return "register_login_logout_profile/login_failed";
    }
    @PostMapping( "/logout")
    public String logoutPage(HttpSession session){
        session.invalidate();
        return "register_login_logout_profile/logout";
    }

//    @PostMapping("/superAdmin/utente/registraAdmin")
//    public ResponseEntity<Void> registraAdmin (@Valid ) {
//        facade.registraAdmin(request);
//        return ResponseEntity.ok().build();
//    }

//    @PutMapping("/authorized/utente/cambiaPassword")
//    //quando uso lo usernamePasswordAuthenticationToken (UPAT) devo passare il token nell'header
//    public ResponseEntity<Void> cambiaPassword
//            (@Valid @RequestBody CambiaPasswordRequestDTO request, UsernamePasswordAuthenticationToken upat){
//        Utente u = (Utente) upat.getPrincipal();
//        facade.cambiaPassword(request, u);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/admin/utente/disattivaCliente/{id}")
//    public ResponseEntity<Void> disattivaCliente(
//            @PathVariable long id,@RequestHeader("User")String emailAdmin){
//        facade.disattivaUtente(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/superAdmin/utente/disattivaAdmin/{id}")
//    public ResponseEntity<Void> disattivaAdmin(
//            @PathVariable long id,@RequestHeader("User")String emailSuperAdmin){
//        facade.disattivaUtente(id);
//        return ResponseEntity.ok().build();
//    }
    //TODO getUtente

}