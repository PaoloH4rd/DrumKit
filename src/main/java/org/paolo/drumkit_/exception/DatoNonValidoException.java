package org.paolo.drumkit_.exception;

import lombok.Data;

@Data
public class DatoNonValidoException extends RuntimeException {
    //caso di DTO con valori non consentiti
    public DatoNonValidoException(String message) {
        super(message);
    }
}
