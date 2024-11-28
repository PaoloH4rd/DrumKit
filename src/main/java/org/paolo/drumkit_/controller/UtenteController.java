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
            return "utente_actions/register";

        }
        if (!facade.login(u.getEmail(), u.getPassword())) {
            // Attempt to register
            facade.registraCliente(u.getNome(), u.getCognome(), u.getEmail(), u.getPassword(), passwordRipetuta);
                // Registration succeeded
                redirectAttributes.addFlashAttribute("success", true);
                return "redirect:/register?success=true";
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
            //TODO TESTARE
            return "/dashboard/dashboard";
        } else {
            // Login failed, pass error parameter
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/login?error=true";
        }
    }


    @PostMapping("/superAdmin/utente/registraAdmin")
    public String registraAdmin (@Valid @ModelAttribute Utente u,BindingResult theBindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam("passwordSuperAdmin") String passwordSuperAdmin){
        //controllo password dell'admin
        if (theBindingResult.hasErrors()) {
            return "utente_actions/register";
        }

        if (!facade.login(u.getEmail(), u.getPassword()) && u.getPassword().equals(passwordSuperAdmin))
            facade.registraAdmin(u.getNome(), u.getCognome(),u.getEmail(), u.getPassword(), passwordSuperAdmin);
        return "redirect:/welcome";
    }

    //si indirizza alla pagina di profilo
    @GetMapping(path = "/all/utente/profile")
    public String profile(Model model, Authentication authentication){
        //si prende lo user dalla sessione e lo si converte in DTO
        Utente Utente = (Utente) authentication.getPrincipal();
        //si prendono le informazioni necessarie dl profilo
        UtenteDTO utenteDTO = utenteFacade.getProfile(Utente);
        model.addAttribute("utenteDTO", utenteDTO);
        return "utente_actions/profile";
    }
    @PostMapping( "/logout")
    public String logoutPage(HttpSession session){
        session.invalidate();
        return "redirect:/login?logout=true";
    }
//    @PutMapping("/authorized/utente/cambiaPassword")

//
//    @PutMapping("/admin/utente/disattivaCliente/{id}")

//
//    @PutMapping("/superAdmin/utente/disattivaAdmin/{id}")

}