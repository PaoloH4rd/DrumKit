package org.paolo.drumkit_.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/areaCliente")
public class ClienteController {
    //dashboard admin
    @GetMapping("")
    public String pannelloAdmin(){
        return "dashboard/vedi_pannello_cliente";
    }
}
