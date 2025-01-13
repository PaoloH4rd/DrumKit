package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.facade.ProdottoFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pannelloAdmin")
public class AdminController {
    private final ProdottoFacade prodottoFacade;

    //dashboard admin
    @GetMapping("")
    public String pannelloAdmin(Model model){
        Utente utenteLoggato = (Utente)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProdottoInVenditaResponseDTO> prodotti = prodottoFacade.getProdotti(utenteLoggato.getId());
        model.addAttribute("prodotti", prodotti);
        return "/dashboard/vedi_pannello_admin";
    }

//    @PutMapping("/approvaProdotto")
//    public String approvaProdotto(@RequestParam Long idProdotto){
//        prodottoFacade.approvaProdotto(idProdotto);
//        return "redirect:/dashboard/pannelloAdmin";
//    }

    @PutMapping("/rifiutaProdotto")
    public String rifiutaProdotto(@RequestParam Long idProdotto,@RequestParam String motivoRifiuto){
        prodottoFacade.rifiutaProdotto(idProdotto);
        return "redirect:/dashboard/pannelloAdmin";
    }

}
