package org.paolo.drumkit_.controller;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.CambiaPasswordRequestDTO;
import org.paolo.drumkit_.dto.request.RegistrazioneUtenteDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteFacade utenteFacade;

    // /chi puo chiama l'api/cosa sto gestendo/ nome dell'api
    @PostMapping("/all/utente/registraCliente")
    public String registraCliente (@ModelAttribute ("registrazioneUtenteDTO") @Valid RegistrazioneUtenteDTO registrazioneUtenteDTO, BindingResult theBindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (theBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrazioneUtenteDTO", theBindingResult);
            redirectAttributes.addFlashAttribute("registrazioneUtenteDTO", registrazioneUtenteDTO);
            return "redirect:/register";
        }
        try {
            utenteFacade.registraCliente(registrazioneUtenteDTO.getNome(), registrazioneUtenteDTO.getCognome(), registrazioneUtenteDTO.getEmail(),
                    registrazioneUtenteDTO.getPassword(), registrazioneUtenteDTO.getPasswordRipetuta(), registrazioneUtenteDTO.getDataNascita());
            // Registration succeeded
            redirectAttributes.addFlashAttribute("successMessage", "Registrazione avvenuta con successo");
            return "redirect:/register";
        }catch (DatoNonValidoException e){
            // Gestione errori personalizzati
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
    //login
    @PostMapping(path = "/all/utente/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password, HttpSession session) {
            // provo login
            if (utenteFacade.login(email, password)) {
                // Login riuscito
                session.setAttribute("email", email);
                return "redirect:/dashboard";
            }
            return "redirect:/login?error=true";
    }

    //cambia password
    @PostMapping("/all/utente/cambiaPassword")
    public String cambiaPassword(
            @ModelAttribute("cambiaPasswordRequest") @Valid CambiaPasswordRequestDTO request,
            BindingResult thebindingresult, RedirectAttributes redirectAttributes, HttpSession session) {
        if (thebindingresult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cambiaPasswordRequest", thebindingresult);
            redirectAttributes.addFlashAttribute("cambiaPasswordRequest", request);
            return "redirect:/dashboard/profilo/cambiaPassword";
        }
        try {
            // Cambia password utente loggato
            utenteFacade.cambiaPassword((String) session.getAttribute("email"), request.getVecchiaPassword(),
                    request.getNuovaPassword(),request.getNuovaPasswordRipetuta());
            redirectAttributes.addFlashAttribute("successMessage", "Password cambiata con successo\n Effettua il login con la nuova password");
            session.invalidate();
            return "redirect:/dashboard/profilo/cambiaPassword";
        } catch (DatoNonValidoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/dashboard/profilo/cambiaPassword";
        }
    }

    @PostMapping( "/logout")
    public String logoutPage(HttpSession session){
        session.invalidate();
        return "redirect:/login?logout=true";
    }
    //ottieni foto
// Ottieni foto
    @GetMapping("/immagine/{filename:.+}")
    public ResponseEntity<Resource> getImmagine(@PathVariable String filename) {
        try {
            // Costruisci il percorso corretto per le immagini
            Path path = Paths.get("src/main/resources/static/styles/images/" + filename);
            Resource resource = new UrlResource(path.toUri());

            // Controlla se il file esiste e se è leggibile
            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                // Logga un messaggio di errore se il file non esiste
                System.err.println("File non trovato: " + path.toString());
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            // Gestisci l'eccezione in caso di errore nella costruzione dell'URL
            System.err.println("Errore nell'URL: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            // Gestisci l'eccezione in caso di errore durante la lettura del file
            System.err.println("Errore nella lettura del file: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}