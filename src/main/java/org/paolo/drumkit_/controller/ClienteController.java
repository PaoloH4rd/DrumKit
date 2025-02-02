package org.paolo.drumkit_.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.ProdottoRequestDTO;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.ProdottoFacade;
import org.paolo.drumkit_.facade.RigaOrdineFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/areaCliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ProdottoFacade prodottoFacade;
    private final RigaOrdineFacade rigaOrdineFacade;

    @GetMapping("")
    public String pannelloCliente(Model model) {
        Utente u = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProdottoInVenditaResponseDTO> prodotti = prodottoFacade.getAllProdottiApprovatiNonDiUtenteLoggato(u.getId());
        model.addAttribute("prodotti", prodotti);
        return "dashboard/vedi_pannello_cliente";
    }

    @GetMapping("/profiloVenditore")
    public String profiloVenditore(@RequestParam ("id") Long id, Model model) {
        //metto il venditore nel model per visualizzarlo tramite UtenteVenditoreResponseDTO
        model.addAttribute("venditore", prodottoFacade.getVenditore(id));
        List<ProdottoInVenditaResponseDTO> prodotti = prodottoFacade.getAllProdottiApprovatiVenditore(prodottoFacade.getVenditore(id).getEmail());
        model.addAttribute("prodotti", prodotti);
        return "dashboard/cliente/vedi_profilo_venditore";
    }

    @GetMapping("/aggiungiProdottoVendita")
    public String aggiungiProdottoVendita(Model model) {
        Utente utente = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!model.containsAttribute("prodottoRequestDTO"))
            model.addAttribute("prodottoRequestDTO", new ProdottoRequestDTO());
        model.addAttribute("nome", utente.getNome());
        return "dashboard/cliente/aggiungi_prodotto_vendita";
    }

    @PostMapping("/aggiungiProdottoVendita")
    public String aggiungiProdottoVendita(@ModelAttribute("prodottoRequestDTO") @Valid ProdottoRequestDTO prodotto,
                                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Utente utenteLoggato = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.prodottoRequestDTO", bindingResult);
            redirectAttributes.addFlashAttribute("prodottoRequestDTO", prodotto);
            return "redirect:/areaCliente/aggiungiProdottoVendita";
        }
            MultipartFile immagine = prodotto.getImmagine();
            if (immagine == null || immagine.isEmpty()) {
                prodottoFacade.aggiungiProdottoVendita(prodotto.getNome(), prodotto.getDescrizione(),
                    prodotto.getPrezzo(), prodotto.getQuantita(), utenteLoggato.getId(),"static/styles/images/item-placeholder.jpg" );

            }else{
                String imageName = prodottoFacade.saveImage(immagine);
                prodottoFacade.aggiungiProdottoVendita(prodotto.getNome(), prodotto.getDescrizione(),
                    prodotto.getPrezzo(), prodotto.getQuantita(), utenteLoggato.getId(),imageName);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Prodotto aggiunto con successo");
            return "redirect:/areaCliente/aggiungiProdottoVendita";

    }
    @PostMapping("/aggiungiProdottoAlCarrello")
    public String aggiungiProdottoAlCarrello(@ModelAttribute("prodottoId") Long prodottoId, @RequestParam int quantita) {
        Utente uLoggato = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rigaOrdineFacade.aggiungiProdottoAlCarrello(prodottoId,quantita,uLoggato.getId());
        return "redirect:/areaCliente/carrello";
    }

}
