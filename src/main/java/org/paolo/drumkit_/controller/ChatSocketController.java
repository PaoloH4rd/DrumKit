package org.paolo.drumkit_.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.facade.ChatFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.mapper.ChatMapper;
import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Messaggio;
import org.paolo.drumkit_.service.def.ChatService;
import org.paolo.drumkit_.service.impl.CustomSenderMessaggioServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {
    private final ChatFacade chatFacade;
    private final UtenteFacade utenteFacade;
    private final ChatService chatService;
    private final CustomSenderMessaggioServiceImpl customSenderMessaggioService;
    private final ChatMapper chatMapper;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper; // Inietta automaticamente il mapper


    @MessageMapping("/chat/private/{chatId}")
    public void sendMessage(@DestinationVariable Long chatId, Messaggio messaggio) {
        // Invia il messaggio alla coda appropriata
        Chat chat = chatService.getById(chatId);
        messaggio.setTesto("Ciao, come stafwi?");
        messaggio.setChat(chat);
        messaggio.setDataOra(LocalDateTime.now());
        MessaggioResponseDTO m = chatMapper.toMessaggioResponseDTO(chat.getUtenteUno(),chat.getUtenteDue(), messaggio);

        try {
            // Converte l'oggetto MessaggioResponseDTO in una stringa JSON
            String json = mapper.writeValueAsString(m);
            System.out.println(json);
//            rabbitTemplate.convertAndSend("ExchangeDurable", "chat." + chatId, json);
            rabbitTemplate.convertAndSend("amq.topic","amq.topic."+chatId, json);
//            rabbitTemplate.convertAndSend("", "Cestino", json);



        } catch (JsonProcessingException e) {
            // Gestisce errori nella serializzazione del JSON
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore nella conversione del messaggio in JSON", e);
        }

    }
}
