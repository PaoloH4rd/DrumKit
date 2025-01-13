package org.paolo.drumkit_.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/areaCliente/chat")
public class ChatController {
    @GetMapping("")
    public String mostraChat() {
        //Model model -> model.addAttribute("chat", chatService.getChat());
        return "vedi_chat";
    }
}
