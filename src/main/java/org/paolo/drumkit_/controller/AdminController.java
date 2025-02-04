package org.paolo.drumkit_.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.BloccaUtenteRequestDTO;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteVenditoreResponseDTO;
import org.paolo.drumkit_.facade.ProdottoFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
//controller per la dashboard dell'admin
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

    //approva prodotto
    @PostMapping("/approvaProdotto")
    public String approvaProdotto(@RequestParam Long idProdotto, RedirectAttributes redirectAttributes){
        prodottoFacade.approvaProdotto(idProdotto);
        redirectAttributes.addFlashAttribute("successMessage", "Prodotto approvato con successo");
        return "redirect:/pannelloAdmin?successMessage=true";
    }
    //rifiuta prodotto
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
    @PostMapping("/disattivaVenditore")
    public String bloccaUtente(@Valid @ModelAttribute ("bloccaUtenteRequestDTO")BloccaUtenteRequestDTO bloccaUtenteRequestDTO, RedirectAttributes redirectAttributes){
        try {
            utenteFacade.disattivaUtente(bloccaUtenteRequestDTO.getEmail());
            redirectAttributes.addFlashAttribute("successMessage", "Utente bloccato con successo");
            return "redirect:/pannelloAdmin/pannelloBlocchi";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/pannelloAdmin/pannelloBlocchi";
        }
    }

    //profilo venditore
    @GetMapping("/profiloVenditore")
    public String profiloVenditore(@RequestParam Long id, Model model) {
        UtenteVenditoreResponseDTO venditore = prodottoFacade.getVenditore(id);
        model.addAttribute("venditore", venditore);
        //vedi prodotti in vendita dell'utente
        List<ProdottoInVenditaResponseDTO> prodottiVenditore = prodottoFacade.getAllProdottiDaApprovareVenditore(venditore.getId());
        model.addAttribute("prodottiVenditore", prodottiVenditore);
        model.addAttribute("bloccaUtenteRequestDTO", new BloccaUtenteRequestDTO());
        return "dashboard/admin/vedi_profilo_venditore_admin";
    }

}
