package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CambiaPasswordRequestDTO {

    @NotBlank(message = "La vecchia password non può essere vuota")
    private String vecchiaPassword;

    @NotBlank(message = "La nuova password non può essere vuota")
    @Size(min = 8, message = "La nuova password deve contenere almeno 8 caratteri")
    private String nuovaPassword;

    @NotBlank(message = "La nuova password ripetuta non può essere vuota")
    @Size(min = 8, message = "La nuova password deve contenere almeno 8 caratteri")
    private String nuovaPasswordRipetuta;

    // Getter e Setter
    public String getVecchiaPassword() {
        return vecchiaPassword;
    }

    public void setVecchiaPassword(String vecchiaPassword) {
        this.vecchiaPassword = vecchiaPassword;
    }

    public String getNuovaPassword() {
        return nuovaPassword;
    }

    public void setNuovaPassword(String nuovaPassword) {
        this.nuovaPassword = nuovaPassword;
    }

    public String getNuovaPasswordRipetuta() {
        return nuovaPasswordRipetuta;
    }

    public void setNuovaPasswordRipetuta(String nuovaPasswordRipetuta) {
        this.nuovaPasswordRipetuta = nuovaPasswordRipetuta;
    }
}
