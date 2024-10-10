package org.paolo.drumkit_.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrazioneRequestDTO {

    //logica sul singolo campo = VALIDATION
    @NotBlank(message = "tutte le persone hanno un nome")
    private String nome;

    @NotBlank(message = "tutte le persone hanno un cognome")
    private String cognome;

    @NotNull(message = "devi inserire una email")
    @Email(message = "deve essere una email")
    private String email;

    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$" ,
            message = "At least 8 chars\n" + "\n" +
            "Contains at least one digit\n" + "\n" +
            "Contains at least one lower alpha char and one upper alpha char\n" + "\n" +
            "Contains at least one char within a set of special chars (@#%$^ etc.)\n" + "\n" +
            "Does not contain space, tab, etc.")
    private String password;
    private String passwordRipetuta;

    @Past(message = "non puoi nascere nel futuro")
    private LocalDate dataNascita;

}
