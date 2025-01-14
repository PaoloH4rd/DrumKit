package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.facade.ProdottoFacade;
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

}
