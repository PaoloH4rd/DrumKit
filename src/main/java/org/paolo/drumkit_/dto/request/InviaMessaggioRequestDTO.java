package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InviaMessaggioRequestDTO {
//    @NotBlank
    @Email(message = "Inserisci un indirizzo email valido")
    private String emailDestinatario;
    @NotBlank
    @Size(min = 1, max = 500, message = "Il messaggio deve essere lungo almeno 1 carattere e massimo 500")
    private String testo;
}
