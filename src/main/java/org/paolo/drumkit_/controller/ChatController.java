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
        if (!model.containsAttribute("inviaMessaggioRequestDTO"))
            model.addAttribute("inviaMessaggioRequestDTO", new InviaMessaggioRequestDTO());
        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //caricare la lista delle chat dell'utente loggato
        List<Chat> chats = chatFacade.getChats(uLoggato.getId());
        //aggiungi al model email utente loggato
        model.addAttribute("emailMittente", uLoggato.getUsername());
        model.addAttribute("chats", chats);

        return "dashboard/cliente/chat_dashboard";
    }

    @PostMapping("/invia")
    public String inviaMessaggio(@Valid @ModelAttribute InviaMessaggioRequestDTO inviaMessaggioRequestDTO,
                                   BindingResult bindingResult,RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.inviaMessaggioRequestDTO", bindingResult);
            redirectAttributes.addFlashAttribute("inviaMessaggioRequestDTO", inviaMessaggioRequestDTO);
            // Aggiungi un messaggio di errore per i problemi di validazione
            return "redirect:/areaCliente/chatDashboard" ;        }
        try {
            Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long idUser = uLoggato.getId();
            chatFacade.inviaMessaggio(idUser, inviaMessaggioRequestDTO);

            // Aggiungi un messaggio di successo
            redirectAttributes.addFlashAttribute("successMessage", "Messaggio inviato con successo.");
            return "redirect:/areaCliente/chatDashboard" ;

        } catch (DatoNonValidoException e) {
            // Aggiungi un messaggio di errore specifico
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/areaCliente/chatDashboard";
        }
    }

    @GetMapping("/apriChatRabbit")
    public String getChatRabbit(@RequestParam("chatId") Long chatId, @RequestParam("email") String email, Model model) {
        if (!model.containsAttribute("inviaMessaggioRequestDTO"))
            model.addAttribute("inviaMessaggioRequestDTO", new InviaMessaggioRequestDTO());
        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nomeDestinatario = utenteFacade.getNomeByEmail(email);
        List<MessaggioResponseDTO> messaggi = chatFacade.getChat(uLoggato.getId(), email);
        model.addAttribute("messaggi", messaggi);
        model.addAttribute("emailDestinatario", email);
        model.addAttribute("nomeDestinatario", nomeDestinatario);
        model.addAttribute("utenteLoggatoEmail", uLoggato.getUsername());
        model.addAttribute("chatId", chatId);
        model.addAttribute("idClient", uLoggato.getId());


        return "dashboard/cliente/chats/chat_fragment";
    }

}
