package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;


@Data
public class CartaDiCreditoRequestDTO {


    @NotBlank
    private String numeroDiCarta;

    @Future
    private LocalDate scadenza;

    @NotBlank(message = "inseririe un codice cvv")
    @Size(min = 3 , max = 3 , message = "il codice cvv deve essere di 3 cifre")
    private String cvv;

    @NotBlank(message = "inserire il nome dell'intestatario")
    private String nome;

    @NotBlank(message = "inserire il cognome dell'intestatario")
    private String cognome;

    @NotNull
    private long idUtente;

}
