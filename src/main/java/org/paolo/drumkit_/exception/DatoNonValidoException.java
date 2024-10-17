package org.paolo.drumkit_.exception;

public class DatoNonValidoException extends RuntimeException {

    //caso di DTO con valori non consentiti
    public DatoNonValidoException(String message){
        super(message);
    }

}
