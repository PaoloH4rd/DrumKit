package org.paolo.drumkit_.controller;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.UtenteDTO;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteFacade facade;
    private final UtenteFacade utenteFacade;


    // /chi puo chiama l'api/cosa sto gestendo/ nome dell'api
    @PostMapping("/all/utente/registraCliente")
    public String registraCliente ( @Valid @ModelAttribute Utente u,BindingResult theBindingResult,
                                    @RequestParam("passwordRipetuta") String passwordRipetuta,
                                    RedirectAttributes redirectAttributes) {
        //si controlla che l'utente non esita già
        if (theBindingResult.hasErrors()) {
            return "register_login_logout_profile/register";

        }
        if (!facade.login(u.getEmail(), u.getPassword())) {
            // Attempt to register
            boolean registrationSuccess = facade.registraCliente(
                    u.getNome(), u.getCognome(), u.getEmail(), u.getPassword(), passwordRipetuta);
            if (registrationSuccess) {
                // Registration succeeded
                redirectAttributes.addFlashAttribute("success", true);
                return "redirect:/register?success=true";
            } else {
                // Registration failed
                redirectAttributes.addFlashAttribute("error", true);
                return "redirect:/register?error=true";
            }
        } else {
            // Registration failed
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/register?error=true";
        }
    }

    @PostMapping(path = "/all/utente/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        // provo login
        if (facade.login(email, password)) {
            // Set the user email in the session upon successful login
            session.setAttribute("email", email);
            //redirect to dashboard
            return "redirect:/dashboard";
        } else {
            // Login failed, pass error parameter
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/login?error=true";
        }
    }

    @PostMapping( "/logout")
    public String logoutPage(HttpSession session){
        session.invalidate();
        return "redirect:/login?logout=true";
    }

    @PostMapping("/superAdmin/utente/registraAdmin")
    public String registraAdmin (@ModelAttribute Utente u,
                                 @RequestParam ("nome") String nome,
                                 @RequestParam ("cognome") String cognome,
                                 @RequestParam ("email") String email,
                                 @RequestParam ("password") String password,
                                 @RequestParam("passwordSuperAdmin") String passwordSuperAdmin){
        //controllo password dell'admin
        if (!facade.login(u.getEmail(), u.getPassword()) && u.getPassword().equals(passwordSuperAdmin))
            facade.registraAdmin(nome,cognome,email, password);

        return "redirect:/welcome";
    }

    //si indirizza alla pagina di profilo
    @GetMapping(path = "/profile")
    public String profile(Model model, Authentication authentication){
        //si prende lo user dalla sessione e lo si converte in DTO
        Utente Utente = (Utente) authentication.getPrincipal();
        //si prendono le informazioni necessarie dl profilo
        UtenteDTO user = utenteFacade.getProfile(Utente);
        model.addAttribute("user", user);
        return "register_login_logout_profile/profile";

    }

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