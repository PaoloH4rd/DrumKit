package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.RegistrazioneUtenteDTO;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {
    @GetMapping("/")
    public String getHomePage() {
        //redirect to /welcome
        return "redirect:/welcome";
    }
    @GetMapping("/welcome")
    public String welcomePage() {
        return "home_page";
    }
    //gets

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        // Controlla se il modello contiene già il DTO
        if (!model.containsAttribute("registrazioneUtenteDTO")) {
            model.addAttribute("registrazioneUtenteDTO", new RegistrazioneUtenteDTO());
        }
        return "register";
    }

}
