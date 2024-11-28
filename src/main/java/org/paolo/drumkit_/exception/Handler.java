package org.paolo.drumkit_.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class Handler {

    @ExceptionHandler({DatoNonValidoException.class})
    public String datoNonValido(Model model, DatoNonValidoException ex){

        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());

        System.out.println(ex.getMessage());
        model.addAttribute("errore", ex.getMessage());
        return "Exceptions/dati_non_validi.html";
    }
    //trigger on response = conflict
    @ExceptionHandler({ResponseStatusException.class})
    public String datoNonValido(Model model, ResponseStatusException ex){
        if (ex.getMessage().equals("409 CONFLICT")) {
            model.addAttribute("errore", "Account già esistente");
            return "Exceptions/dati_non_validi.html";

        }
        if (ex.getMessage().equals("404 NOT_FOUND")) {
            model.addAttribute("errore", "Account non esistente");
            return "Exceptions/dati_non_validi.html";
        }
        return "Exceptions/dati_non_validi.html";
    }
}
