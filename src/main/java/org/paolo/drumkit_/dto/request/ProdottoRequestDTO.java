package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProdottoRequestDTO {
    @NotNull
    private String nome;
    @NotNull
    private String descrizione;
    @NotNull(message = "Il prezzo è obbligatorio.")
    @Positive(message = "Il prezzo non può essere negativo o zero.")
    @Digits(integer = 4, fraction = 2, message = "Il prezzo può avere al massimo 4 cifre intere e 2 decimali.")
    private Double prezzo;

    @NotNull(message = "La quantità è obbligatoria.")
    @Positive(message = "La quantità non può essere negativa o zero.")
    @Digits(integer = 1, fraction = 0, message = "La quantità deve essere un numero intero tra 1 e 9.")
    private int quantita;

}
