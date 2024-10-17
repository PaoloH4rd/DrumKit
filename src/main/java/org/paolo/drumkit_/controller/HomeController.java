package org.paolo.drumkit_.controller;

import jakarta.servlet.http.HttpSession;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // Use @Controller instead of @RestController
public class HomeController {

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

    @GetMapping(path = "/login")
    public String loginPage(){
        return "register_login_logout_profile/login";
    }

    //redirect a login failed
    @GetMapping("/login_failed")
    public String loginFailed() {
        return "register_login_logout_profile/login_failed";
    }

    //redirect a registrazione fallita
    @GetMapping("/registration_failed")
    public String registrazioneFallita() {
        return "register_login_logout_profile/registration_failed";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "register_login_logout_profile/register";
    }

    //get dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";
    }
}
