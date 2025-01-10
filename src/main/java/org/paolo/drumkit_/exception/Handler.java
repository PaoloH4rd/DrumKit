package org.paolo.drumkit_.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(DatoNonValidoException.class)
    public ModelAndView handleDatoNonValido(DatoNonValidoException ex) {
        ModelAndView modelAndView = new ModelAndView("Exceptions/dati_non_validi");
        modelAndView.addObject("errore", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleResponseStatusException(ResponseStatusException ex) {
        ModelAndView modelAndView = new ModelAndView("Exceptions/dati_non_validi");
        String errorMessage = switch (ex.getStatusCode().toString()) {
            case "409 CONFLICT" -> "Account già esistente";
            case "404 NOT_FOUND" -> "Account non esistente";
            case "403 FORBIDDEN" -> "Accesso non consentito";
            case "401 UNAUTHORIZED" -> "Accesso non autorizzato";
            case "400 BAD_REQUEST" -> "Richiesta non valida";
            default -> "Errore generico";
        };

        modelAndView.addObject("errore", errorMessage);
        return modelAndView;
    }
}
