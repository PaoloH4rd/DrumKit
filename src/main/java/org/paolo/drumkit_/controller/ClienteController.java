package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.ProdottoRequestDTO;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.ProdottoFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/areaCliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ProdottoFacade prodottoFacade;

    @GetMapping("")
    public String pannelloCliente(Model model) {
        Utente u = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProdottoInVenditaResponseDTO> prodotti = prodottoFacade.getProdotti(u.getId());
        model.addAttribute("prodotti", prodotti);
        return "dashboard/vedi_pannello_cliente";
    }

    @GetMapping("/profiloVenditore/{id}")
    public String profiloVenditore(@RequestParam Long id, Model model) {
        model.addAttribute("venditore", prodottoFacade.getVenditore(id));
        return "dashboard/cliente/vedi_profilo_venditore";
    }

    @GetMapping("/aggiungiProdottoVendita")
    public String aggiungiProdottoVendita(Model model) {
        Utente utente = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("prodottoRequestDTO", new ProdottoRequestDTO());
        model.addAttribute(utente.getNome());
        return "dashboard/cliente/aggiungi_prodotto_vendita";
    }

    @PostMapping("/aggiungiProdottoVendita")
    public String aggiungiProdottoVendita(@ModelAttribute("prodottoRequestDTO") ProdottoRequestDTO prodotto,
                                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/areaCliente/aggiungiProdottoVendita";
        }
        try {
            prodottoFacade.aggiungiProdottoVendita(prodotto.getNome(), prodotto.getDescrizione(), prodotto.getPrezzo(), prodotto.getQuantita());
            redirectAttributes.addFlashAttribute("successMessage", "Prodotto aggiunto con successo");
            return "redirect:/areaCliente/aggiungiProdottoVendita?successMessage=true";
        } catch (DatoNonValidoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/areaCliente/aggiungiProdottoVendita?errorMessage=true";
        }
    }

    @PostMapping("/aggiungiProdottoAlCarrello")
    public String aggiungiProdottoAlCarrello(@ModelAttribute("prodottoId") Long prodottoId) {

//        prodottoService.aggiungiProdottoAlCarrello(prodottoId);
        return "redirect:/areaCliente";
    }
}
