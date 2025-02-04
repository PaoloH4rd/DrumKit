package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.CambiaPasswordRequestDTO;
import org.paolo.drumkit_.dto.response.UtenteResponseDTO;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final UtenteFacade utenteFacade;

    // Mostra la dashboard
    @GetMapping("")
    public String dashboard(Model model) {
            // Ottieni l'utente autenticato
            Utente utente = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("nome", utente.getNome());
            return "vedi_dashboard";
        }
    // Mostra il profilo dell'utente
    @GetMapping("/profilo")
    public String getUserProfile (Model model){
        // Ottieni l'utente autenticato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente utente = (Utente) authentication.getPrincipal();
        UtenteResponseDTO utenteResponseDTO = utenteFacade.getProfile(utente);

        model.addAttribute("utenteResponseDTO", utenteResponseDTO);
        return "dashboard/vedi_profilo";
    }
    // Mostra il form per cambiare la password
    @GetMapping("/profilo/cambiaPassword")
    public String mostraForm (Model model){
    if (!model.containsAttribute("cambiaPasswordRequest"))
            model.addAttribute("cambiaPasswordRequest", new CambiaPasswordRequestDTO());
        return "dashboard/profilo/cambia_password";

     }
}
