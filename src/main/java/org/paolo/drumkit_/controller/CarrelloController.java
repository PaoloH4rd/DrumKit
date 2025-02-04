package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.RigaOrdineFacade;
import org.paolo.drumkit_.model.Ordine;
import org.paolo.drumkit_.model.RigaOrdine;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/areaCliente/carrello")
@Controller
@RequiredArgsConstructor
public class CarrelloController {
    private final RigaOrdineFacade rigaOrdineFacade;

    @GetMapping("")
    //visualizza il carrello
    public String pannelloCarrello(Model model, RedirectAttributes redirectAttributes) {
        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RigaOrdine> righeOrdine = rigaOrdineFacade.getProdottiCarrello(uLoggato.getId());
        double totaleCarrello = rigaOrdineFacade.getTotaleCarrello(uLoggato.getId());

        model.addAttribute("righeOrdine", righeOrdine);
        model.addAttribute("totaleCarrello", totaleCarrello);
        return "dashboard/cliente/vedi_carrello";
    }

    //conferma carrello
    @PostMapping("/confermaCarrello")
    public String confermaCarrello( RedirectAttributes redirectAttributes) {
        Utente uLoggato = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Ordine o = rigaOrdineFacade.confermaCarrello(uLoggato.getId());

        } catch (DatoNonValidoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/areaCliente/carrello";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Ordine confermato con successo");
        return "redirect:/areaCliente/carrello";
    }

    //cambia quantità prodotto
    @PostMapping("/cambiaQuantita")
    public String cambiaQuantita(@RequestParam Long idProdotto, @RequestParam int quantita) {
        Utente uLoggato = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rigaOrdineFacade.cambiaQuantita(idProdotto, quantita, uLoggato.getId());
        return "redirect:/areaCliente/carrello";
    }
    //rimuovi prodotto dal carrello
    @PostMapping("/rimuoviProdotto")
    public String rimuoviProdotto(@RequestParam Long idProdotto) {
        Utente uLoggato = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rigaOrdineFacade.rimuoviProdotto(idProdotto, uLoggato.getId());
        return "redirect:/areaCliente/carrello";
    }

}
