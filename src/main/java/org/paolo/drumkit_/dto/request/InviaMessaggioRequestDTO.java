package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InviaMessaggioRequestDTO {
    private String emailMittente;
    private String emailDestinatario;
    private String testo;
    private String data;
}
