package org.paolo.drumkit_.controller;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.CambiaPasswordRequestDTO;
import org.paolo.drumkit_.dto.request.RegistrazioneUtenteDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteFacade utenteFacade;

    // /chi puo chiama l'api/cosa sto gestendo/ nome dell'api
    @PostMapping("/all/utente/registraCliente")
    public String registraCliente (@ModelAttribute ("registrazioneUtenteDTO") @Valid RegistrazioneUtenteDTO registrazioneUtenteDTO, BindingResult theBindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (theBindingResult.hasErrors()) {
            return "redirect:/register";
        }
        try {
            utenteFacade.registraCliente(registrazioneUtenteDTO.getNome(), registrazioneUtenteDTO.getCognome(), registrazioneUtenteDTO.getEmail(),
                    registrazioneUtenteDTO.getPassword(), registrazioneUtenteDTO.getPasswordRipetuta(), registrazioneUtenteDTO.getDataNascita());
            // Registration succeeded
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione avvenuta con successo");
            return "redirect:/register?successMessage=true";
        }catch (DatoNonValidoException e){
            // Gestione errori personalizzati
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register?errorMessage=true";
        }
    }

    @PostMapping(path = "/all/utente/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // provo login
            if (utenteFacade.login(email, password)) {
                // Set the user email in the session upon successful login
                session.setAttribute("email", email);
                return "redirect:/dashboard";
            }
        } catch (DatoNonValidoException e) {
            // Gestione errori personalizzati
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login?error=true";
        }
        return "redirect:/login?error=true";
    }
    @PostMapping("/all/utente/cambiaPassword")
    public String cambiaPassword(
            @ModelAttribute("cambiaPasswordRequest") @Valid CambiaPasswordRequestDTO request,
            BindingResult thebindingresult, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // Verifica errori di validazione
            if (thebindingresult.hasErrors()) {
                return "redirect:/dashboard/profilo/cambia_password";
            }
            utenteFacade.cambiaPassword((String) session.getAttribute("email"), request.getVecchiaPassword(),
                    request.getNuovaPassword(),request.getNuovaPasswordRipetuta());
            redirectAttributes.addFlashAttribute("successMessage", "Password cambiata con successo\n Effettua il login con la nuova password");
            session.invalidate();
            return "redirect:/dashboard/profilo/cambiaPassword?success=true";

        } catch (DatoNonValidoException e) {
            // Gestione errori personalizzati
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/dashboard/profilo/cambiaPassword?error=true";
        }
    }

//    @PostMapping("/superAdmin/utente/registraAdmin")
//    public String registraAdmin (@Valid @ModelAttribute Utente u,BindingResult theBindingResult,
//                                 @RequestParam("passwordSuperAdmin") String passwordSuperAdmin){
//        //controllo password dell'admin
//        if (theBindingResult.hasErrors()) {
//            return "redirect:/register";
//        }
//        utenteFacade.registraAdmin(u.getNome(), u.getCognome(),u.getEmail(), u.getPassword(), passwordSuperAdmin);
//        return "redirect:/welcome";
//    }

    @PostMapping( "/logout")
    public String logoutPage(HttpSession session){
        session.invalidate();
        return "redirect:/login?logout=true";
    }




//    @PutMapping("/admin/utente/disattivaCliente/{id}")

//
//    @PutMapping("/superAdmin/utente/disattivaAdmin/{id}")
}