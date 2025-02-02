package org.paolo.drumkit_.controller;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.facade.ChatFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {
    private final ChatFacade chatFacade;
    private final UtenteFacade utenteFacade;

    @MessageMapping("/chat/private/{chatId}")
    public void sendMessage(@DestinationVariable Long chatId, InviaMessaggioRequestDTO messaggio) {
        Utente uLoggato = utenteFacade.getByEmail(messaggio.getEmailMittente());
        chatFacade.inviaMessaggio(uLoggato.getId(), messaggio);
    }
}
