package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dashboard/pannelloSuperAdmin")
public class SuperAdminController {
    private final UtenteFacade utenteFacade;
    //dashboard admin
    @GetMapping("")
    public String pannelloAdmin(){
        return "/dashboard/vedi_pannello_superadmin";
    }
    @PostMapping("/disattivaAdmin")
    public String disattivaAdmin(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            utenteFacade.disattivaUtente(email);
            redirectAttributes.addFlashAttribute("successMessage", "Admin disattivato con successo");
            return "redirect:/dashboard/pannelloSuperAdmin?succesMessage=true";
        }catch (HttpStatusCodeException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/dashboard/pannelloSuperAdmin?errorMessage=true";
        }
    }
//    @PostMapping("/aggiungiAdmin")
//    public String aggiungiAdmin(@RequestParam String email, RedirectAttributes redirectAttributes) {

}
