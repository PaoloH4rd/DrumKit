package org.paolo.drumkit_.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/areaCliente/chat")
public class ChatController {
    @GetMapping("")
    public String chats(Model model) {
        return "dashboard/cliente/vedi_chats";
    }
}
