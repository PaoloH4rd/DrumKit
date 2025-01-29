package org.paolo.drumkit_.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    // TODO Messaggio privato
    public void sendPrivateMessage(String idUtente, String message, Long idChat) {
        String destination = "/topic/" + idChat + "/" + idUtente; // /app
        messagingTemplate.convertAndSend(destination, message);
    }
}