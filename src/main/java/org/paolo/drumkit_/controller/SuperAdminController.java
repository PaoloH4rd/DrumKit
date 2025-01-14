package org.paolo.drumkit_.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.RegistrazioneUtenteDTO;
import org.paolo.drumkit_.dto.response.AdminDisattivaSelectResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pannelloSuperAdmin")
public class SuperAdminController {
    private final UtenteFacade utenteFacade;
    //dashboard admin
    @GetMapping("")
    public String pannelloAdmin(Model model){
        model.addAttribute("registrazioneUtenteDTO", new RegistrazioneUtenteDTO());

        // Recupera la lista degli admin dal servizio
        List<AdminDisattivaSelectResponseDTO> admins = utenteFacade.getAllActiveAdmins();

        // Aggiungi la lista al modello
        model.addAttribute("admins", admins);
        return "/dashboard/vedi_pannello_superadmin";
    }


    @PostMapping("/disattivaAdmin")
    public String disattivaAdmin(@RequestParam("adminId") Long adminId,RedirectAttributes redirectAttributes) {
        try {
            utenteFacade.disattivaUtente(adminId);
            redirectAttributes.addFlashAttribute("successMessage", "Admin disattivato con successo");
            return "redirect:/pannelloSuperAdmin?succesMessage=true";
        }catch (HttpStatusCodeException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/pannelloSuperAdmin?errorMessage=true";
        }
    }
    @PostMapping("/aggiungiAdmin")
      public String aggiungiAdmin(@ModelAttribute ("registrazioneUtenteDTO") @Valid RegistrazioneUtenteDTO registrazioneUtenteDTO,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/pannelloSuperAdmin";
        }
        try {
            utenteFacade.registraAdmin(registrazioneUtenteDTO.getNome(), registrazioneUtenteDTO.getCognome(), registrazioneUtenteDTO.getEmail(),
                    registrazioneUtenteDTO.getPassword(), registrazioneUtenteDTO.getPasswordRipetuta(), registrazioneUtenteDTO.getDataNascita());
            redirectAttributes.addFlashAttribute("successMessage", "Admin aggiunto con successo");
            return "redirect:/pannelloSuperAdmin?successMessage=true";
        }catch (DatoNonValidoException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/pannelloSuperAdmin?errorMessage=true";
        }
    }
}
