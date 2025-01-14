package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.facade.RigaOrdineFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/areaCliente/carrello")
@Controller
@RequiredArgsConstructor
public class CarrelloController {
    private final RigaOrdineFacade rigaOrdineFacade;

    @GetMapping("")
    public String pannelloCarrello(Model model) {
        Utente utente = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProdottoInVenditaResponseDTO> prodotti = rigaOrdineFacade.getProdottiCarrello(utente.getId());
        model.addAttribute("prodotti", prodotti);
        return "dashboard/cliente/vedi_carrello";
    }

    @PostMapping("/aggiungiProdottoAlCarrello")
    public String aggiungiProdottoAlCarrello(@ModelAttribute("prodottoId") Long prodottoId, @RequestParam int quantita) {

        rigaOrdineFacade.aggiungiProdottoAlCarrello(prodottoId,quantita);
        return "redirect:/areaCliente";
    }

}
