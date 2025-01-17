package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InviaMessaggioRequestDTO {
    @Email
    private String emailMittente;
    @Email
    private String emailDestinatario;
    @NotNull
    @NotBlank
    private String testo;
    private String data;
}
