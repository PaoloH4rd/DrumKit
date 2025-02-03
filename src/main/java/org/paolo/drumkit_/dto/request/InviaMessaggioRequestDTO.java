package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InviaMessaggioRequestDTO {

    private String emailMittente;
    @Email
    private String emailDestinatario;
    @NotNull
    @NotBlank(message = "Il campo testo non può essere vuoto")
    private String testo;
    private String data;
}
