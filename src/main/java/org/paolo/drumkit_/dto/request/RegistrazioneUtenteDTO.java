package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
public class RegistrazioneUtenteDTO {
    @NotNull
    private String nome;
    @NotNull
    private String cognome;

    @NotNull(message = "L'email non può essere vuota")
    @Email(message = "Inserisci un'email valida")
    private String email;

    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    private String password;

    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    private String passwordRipetuta;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dataNascita;

}
