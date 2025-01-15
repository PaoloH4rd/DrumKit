package org.paolo.drumkit_.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.ChatFacade;
import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Utente;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/areaCliente/chatDashboard")
@RequiredArgsConstructor
public class ChatController {

    private final ChatFacade chatFacade;


    @GetMapping("")
    public String chats(Model model) {
        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //caricare la lista delle chat dell'utente loggato
        List<Chat> chats = chatFacade.getChats(uLoggato.getId());
        model.addAttribute("chats", chats);
        model.addAttribute("inviaMessaggioRequestDTO", new InviaMessaggioRequestDTO());
        return "dashboard/cliente/chat_dashboard";
    }

    @PostMapping("/invia")
    public String inviaMessaggio(@Valid @ModelAttribute InviaMessaggioRequestDTO inviaMessaggioRequestDTO,
                                  RedirectAttributes redirectAttributes, BindingResult bindingResult,
                                  @RequestHeader(value = "referer", required = false) final String referer){
//        String newReferer =referer.replace("?errorMessage=true", "").replace("?successMessage=true", "");

        if (bindingResult.hasErrors()) {
            return "dashboard/cliente/chat_dashboard";
        }
        try {
            Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long idUser = uLoggato.getId();
            chatFacade.inviaMessaggio(idUser, inviaMessaggioRequestDTO);
//            redirectAttributes.addFlashAttribute("successMessage", "Messaggio inviato con successo");
            //redirect  alla stessa pagina
//            return "redirect:" + newReferer + "?successMessage=true";
            return "redirect:" + referer;

        } catch (DatoNonValidoException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            //redirect  alla stessa pagina
//            return "redirect:" + newReferer + "?errorMessage=true";
            return "redirect:" + referer;
        }

    }

    //apri chat con utente email passato come parametro
    @GetMapping("/apriChat")
    public String getChat(@RequestParam("email") String email, Model model) {
        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (email.contains("?successMessage=true") || email.contains("?errorMessage=true")) {
//            email = email.replace("?successMessage=true", "").replace("?errorMessage=true", "");
//        }

        // Ottieni i messaggi tra l'utente loggato e il destinatario
        List<MessaggioResponseDTO> messaggi = chatFacade.getChat(uLoggato.getId(), email);
        model.addAttribute("messaggi", messaggi);
        model.addAttribute("destinatario", email);
        model.addAttribute("utenteLoggato", uLoggato.getUsername());
        model.addAttribute("inviaMessaggioRequestDTO", new InviaMessaggioRequestDTO());
        return "dashboard/cliente/chats/mostra_chat";
    }

}
