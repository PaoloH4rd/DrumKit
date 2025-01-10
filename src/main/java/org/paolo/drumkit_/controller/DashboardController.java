package org.paolo.drumkit_.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.CambiaPasswordRequestDTO;
import org.paolo.drumkit_.dto.response.UtenteDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final UtenteFacade utenteFacade;
    @GetMapping("")
    public String dashboard() {
        return "/vedi_dashboard";
    }

    @GetMapping("/profilo")
    public String getUserProfile(Model model){
        // Ottieni l'utente autenticato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente utente = (Utente) authentication.getPrincipal();
        UtenteDTO utenteDTO = utenteFacade.getProfile(utente);
        model.addAttribute("utente",utenteDTO );
        return "/dashboard/vedi_profilo";
    }
    @GetMapping("/profilo/cambiaPassword")
    public String mostraForm(Model model) {
        model.addAttribute("cambiaPasswordRequest", new CambiaPasswordRequestDTO());
        return "/dashboard/profilo/cambia_password";
    }


}
