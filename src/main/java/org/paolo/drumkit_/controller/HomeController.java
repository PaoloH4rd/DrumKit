package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final UtenteFacade utenteFacade;

    @GetMapping("/")  // Use @GetMapping instead of @RequestMapping
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
        return "/login";
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "/register";
    }
}
