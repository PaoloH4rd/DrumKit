package org.paolo.drumkit_.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.ChatFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
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
    private final UtenteFacade utenteFacade;


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
        if (bindingResult.hasErrors()) {
            // Aggiungi un messaggio di errore per i problemi di validazione
            redirectAttributes.addFlashAttribute("errorMessage", "Errore di validazione. Controlla i dati inseriti.");
            return "redirect:" + referer;        }
        try {
            Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long idUser = uLoggato.getId();
            chatFacade.inviaMessaggio(idUser, inviaMessaggioRequestDTO);

            // Aggiungi un messaggio di successo
            redirectAttributes.addFlashAttribute("successMessage", "Messaggio inviato con successo.");
            return "redirect:" + referer;

        } catch (DatoNonValidoException e) {
            // Aggiungi un messaggio di errore specifico
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:" + referer;
        }
    }

    //apri chat con utente email passato come parametro
//    @GetMapping("/apriChat")
//    public String getChat(@RequestParam("email") String email, Model model) {
//        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String nomeDestinatario = utenteFacade.getNomeByEmail(email);
//        Long chatId = chatFacade.getChatId(uLoggato.getId(), email);
//        // Ottieni i messaggi tra l'utente loggato e il destinatario
//        List<MessaggioResponseDTO> messaggi = chatFacade.getChat(uLoggato.getId(), email);
//        model.addAttribute("messaggi", messaggi);
//        model.addAttribute("emailDestinatario", email);
//        model.addAttribute("nomeDestinatario", nomeDestinatario);
//        model.addAttribute("utenteLoggatoEmail", uLoggato.getUsername());
//        model.addAttribute("inviaMessaggioRequestDTO", new InviaMessaggioRequestDTO());
//        model.addAttribute("chatId", chatId); // Aggiungi l'ID della chat al modello
//        return "dashboard/cliente/chats/mostra_chat";
//    }

    @GetMapping("/apriChatRabbit")
    public String getChatRabbit(@RequestParam("chatId") Long chatId, @RequestParam("email") String email, Model model) {
        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nomeDestinatario = utenteFacade.getNomeByEmail(email);
        List<MessaggioResponseDTO> messaggi = chatFacade.getChat(uLoggato.getId(), email);
        model.addAttribute("messaggi", messaggi);
        model.addAttribute("emailDestinatario", email);
        model.addAttribute("nomeDestinatario", nomeDestinatario);
        model.addAttribute("utenteLoggatoEmail", uLoggato.getUsername());
        model.addAttribute("inviaMessaggioRequestDTO", new InviaMessaggioRequestDTO());
        model.addAttribute("chatId", chatId);
        return "dashboard/cliente/chats/mostra_chat_rabbit";
    }




}
