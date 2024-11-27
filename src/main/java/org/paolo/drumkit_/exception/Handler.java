package org.paolo.drumkit_.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    @ExceptionHandler({DatoNonValidoException.class})
    public String datoNonValido(Model model, DatoNonValidoException ex){
        model.addAttribute("errore", ex.getMessage());
        return "templates/Exceptions/dati_non_validi.html";
    }
}
