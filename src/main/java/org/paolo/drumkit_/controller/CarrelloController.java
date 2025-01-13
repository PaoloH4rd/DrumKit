package org.paolo.drumkit_.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/areaCliente/carrello")
@Controller
public class CarrelloController {
    @GetMapping("")
    public String mostraCarrello() {
        return "vedi_carrello";
    }
}
