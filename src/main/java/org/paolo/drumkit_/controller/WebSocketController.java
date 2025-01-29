package org.paolo.drumkit_.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Aggiungi un metodo per registrare gli argomenti di messaggio
    @MessageMapping("/send")
    public void sendMessage(String message) {
        System.out.println("Message: " + message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
