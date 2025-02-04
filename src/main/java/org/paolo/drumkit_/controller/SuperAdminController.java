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
        if (!model.containsAttribute("registrazioneUtenteDTO"))
         model.addAttribute("registrazioneUtenteDTO", new RegistrazioneUtenteDTO());

        // Recupera la lista degli admin dal servizio
        List<AdminDisattivaSelectResponseDTO> admins = utenteFacade.getAllActiveAdmins();

        // Aggiungi la lista al modello
        model.addAttribute("admins", admins);
        return "/dashboard/vedi_pannello_superadmin";
    }

    //disattiva admin
    @PostMapping("/disattivaAdmin")
    public String disattivaAdmin(@RequestParam("adminId") Long adminId,RedirectAttributes redirectAttributes) {
        
            utenteFacade.disattivaAdmin(adminId);
            redirectAttributes.addFlashAttribute("successMessage", "Admin disattivato con successo");
            return "redirect:/pannelloSuperAdmin";
    }
    //aggiungi admin
    @PostMapping("/aggiungiAdmin")
      public String aggiungiAdmin(@ModelAttribute ("registrazioneUtenteDTO") @Valid RegistrazioneUtenteDTO registrazioneUtenteDTO,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrazioneUtenteDTO", bindingResult);
            redirectAttributes.addFlashAttribute("registrazioneUtenteDTO", registrazioneUtenteDTO);
            return "redirect:/pannelloSuperAdmin";
        }
        try {
            // Registra l'admin
            utenteFacade.registraAdmin(registrazioneUtenteDTO.getNome(), registrazioneUtenteDTO.getCognome(), registrazioneUtenteDTO.getEmail(),
                    registrazioneUtenteDTO.getPassword(), registrazioneUtenteDTO.getPasswordRipetuta(), registrazioneUtenteDTO.getDataNascita());
            redirectAttributes.addFlashAttribute("successMessage", "Admin aggiunto con successo");
            return "redirect:/pannelloSuperAdmin";
        }catch (DatoNonValidoException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/pannelloSuperAdmin";
        }
    }
}
