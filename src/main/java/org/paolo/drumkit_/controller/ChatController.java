package org.paolo.drumkit_.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.ChatFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Messaggio;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.service.def.ChatService;
import org.paolo.drumkit_.service.impl.CustomSenderMessaggioServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/areaCliente/chatDashboard")
@RequiredArgsConstructor
public class ChatController {

    private final ChatFacade chatFacade;
    private final UtenteFacade utenteFacade;
    private final ChatService chatService;
    private final CustomSenderMessaggioServiceImpl customSenderMessaggioService;

    @GetMapping("")
    public String chats(Model model) {
        if (!model.containsAttribute("inviaMessaggioRequestDTO"))
            model.addAttribute("inviaMessaggioRequestDTO", new InviaMessaggioRequestDTO());
        Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //caricare la lista delle chat dell'utente loggato
        List<Chat> chats = chatFacade.getChats(uLoggato.getId());
        model.addAttribute("chats", chats);

        return "dashboard/cliente/chat_dashboard";
    }

//    @MessageMapping("/chat/private/{chatId}")
//    public void sendPrivateMessage(@DestinationVariable Long chatId, Messaggio messaggio) {
//        Utente u=  (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Chat chat = chatService.getById(chatId);
//        System.out.println("chatId: "+chatId+ " messaggio: "+messaggio.getTesto());
//        // Associa il messaggio alla chat
//        messaggio.setChat(chat);
//        // il messaggio è stato inviato dall'utente loggato
//        //rispondo
//        String topic = "topicDiEsempio";
//        customSenderMessaggioService.sendPrivateMessage(u.getId(), messaggio.getTesto(), chatId);
//    }
//    @PostMapping("/invia")
//    public String inviaMessaggio(@Valid @ModelAttribute InviaMessaggioRequestDTO inviaMessaggioRequestDTO,
//                                   BindingResult bindingResult,RedirectAttributes redirectAttributes,
//                                  @RequestHeader(value = "referer", required = false) final String referer){
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.inviaMessaggioRequestDTO", bindingResult);
//            redirectAttributes.addFlashAttribute("inviaMessaggioRequestDTO", inviaMessaggioRequestDTO);
//            // Aggiungi un messaggio di errore per i problemi di validazione
//            return "redirect:" + referer;        }
//        try {
//            Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            long idUser = uLoggato.getId();
//            chatFacade.inviaMessaggio(idUser, inviaMessaggioRequestDTO);
//
//            // Aggiungi un messaggio di successo
//            redirectAttributes.addFlashAttribute("successMessage", "Messaggio inviato con successo.");
//            return "redirect:" + referer;
//
//        } catch (DatoNonValidoException e) {
//            // Aggiungi un messaggio di errore specifico
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            return "redirect:" + referer;
//        }
//    }

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

        return "dashboard/cliente/chats/mostra_chat_test";
//        return "dashboard/cliente/chats/mostra_chat_rabbit";
    }


    @PostMapping("/invia")
    public void inviaMessaggioRabbit(@Valid @ModelAttribute InviaMessaggioRequestDTO inviaMessaggioRequestDTO,
                                     RedirectAttributes redirectAttributes, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Aggiungi un messaggio di errore per i problemi di validazione
            redirectAttributes.addFlashAttribute("errorMessage", "Errore di validazione. Controlla i dati inseriti.");
            return;        }
        try {
            Utente uLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long idUser = uLoggato.getId();
            chatFacade.inviaMessaggio(idUser, inviaMessaggioRequestDTO);

            // Aggiungi un messaggio di successo
            redirectAttributes.addFlashAttribute("successMessage", "Messaggio inviato con successo.");

        } catch (DatoNonValidoException e) {
            // Aggiungi un messaggio di errore specifico
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
    }

}
