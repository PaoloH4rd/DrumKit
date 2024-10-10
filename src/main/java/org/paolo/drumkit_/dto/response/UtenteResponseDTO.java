package org.paolo.drumkit_.dto.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UtenteResponseDTO {
    @NotBlank
    private long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank
    private String email;

    @NotNull
    @Past
    private LocalDate dataNascita;

    @NotNull
    private String ruolo;

}
