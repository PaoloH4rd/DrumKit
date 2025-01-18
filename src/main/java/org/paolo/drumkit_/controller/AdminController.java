package org.paolo.drumkit_.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.BloccaUtenteRequestDTO;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteResponseDTO;
import org.paolo.drumkit_.facade.ProdottoFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pannelloAdmin")
public class AdminController {
    private final ProdottoFacade prodottoFacade;
    private final UtenteFacade utenteFacade;

    //dashboard admin
    @GetMapping("")
    public String pannelloAdmin(Model model){
        List<ProdottoInVenditaResponseDTO> prodotti = prodottoFacade.getAllProdottiDaApprovare();
        model.addAttribute("prodotti", prodotti);
        return "/dashboard/vedi_pannello_admin";
    }

    @PostMapping("/approvaProdotto")
    public String approvaProdotto(@RequestParam Long idProdotto, RedirectAttributes redirectAttributes){
        prodottoFacade.approvaProdotto(idProdotto);
        redirectAttributes.addFlashAttribute("successMessage", "Prodotto approvato con successo");
        return "redirect:/pannelloAdmin?successMessage=true";
    }

    @PostMapping("/rifiutaProdotto")
    public String rifiutaProdotto(@RequestParam Long idProdotto, RedirectAttributes redirectAttributes){
        prodottoFacade.rifiutaProdotto(idProdotto);
        redirectAttributes.addFlashAttribute("successMessage", "Prodotto rifiutato con successo");
        return "redirect:/pannelloAdmin?successMessage=true";
    }


    //pagina per bloccare gli utenti
    @GetMapping("/pannelloBlocchi")
    public String bloccaUtente(Model model){
        if (!model.containsAttribute("bloccaUtenteRequestDTO")) {
            model.addAttribute("bloccaUtenteRequestDTO", new BloccaUtenteRequestDTO());
        }
        //mostra utenti bloccati
        List<UtenteResponseDTO> utentiBloccati = utenteFacade.getUtentiBloccati();
        model.addAttribute("utentiBloccati", utentiBloccati);

        return "dashboard/admin/pannello_blocchi";
    }
    //blocca utente
    @PostMapping("/bloccaUtente")
    public String bloccaUtente(@Valid @ModelAttribute ("bloccaUtenteRequestDTO")BloccaUtenteRequestDTO bloccaUtenteRequestDTO, RedirectAttributes redirectAttributes){
        utenteFacade.disattivaUtente(bloccaUtenteRequestDTO.getEmail());
        redirectAttributes.addFlashAttribute("successMessage", "Utente bloccato con successo");
        return "redirect:/pannelloAdmin";
    }
    @PostMapping("/sbloccaUtente")
    public String sbloccaUtente(@RequestParam String email, RedirectAttributes redirectAttributes){
        utenteFacade.attivaUtente(email);
        redirectAttributes.addFlashAttribute("successMessage", "Utente sbloccato con successo");
        return "redirect:/pannelloAdmin";
    }
    @GetMapping("/profiloVenditore")
    public String profiloVenditore(@RequestParam Long id, Model model) {
        model.addAttribute("venditore", prodottoFacade.getVenditore(id));
        //vedi prodotti in vendita dell'utente
        List<ProdottoInVenditaResponseDTO> prodottiVenditore = prodottoFacade.getAllProdottiDaApprovareVenditore(id);
        model.addAttribute("prodottiVenditore", prodottiVenditore);
        return "dashboard/admin/vedi_profilo_venditore_admin";
    }

}
