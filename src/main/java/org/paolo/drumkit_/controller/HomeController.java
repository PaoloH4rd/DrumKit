package org.paolo.drumkit_.controller;

import jakarta.servlet.http.HttpSession;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "notLogged", required = false) String notLogged) {

        return "register_login_logout_profile/login";
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
