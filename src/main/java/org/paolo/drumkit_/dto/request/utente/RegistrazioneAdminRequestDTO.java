package org.paolo.drumkit_.dto.request.utente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrazioneAdminRequestDTO{
    @NotNull(message = "devi inserire una email")
    @Email(message = "deve essere una email")
    private String emailAdmin;
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$" ,
            message = "At least 8 chars\n" + "\n" +
                    "Contains at least one digit\n" + "\n" +
                    "Contains at least one lower alpha char and one upper alpha char\n" + "\n" +
                    "Contains at least one char within a set of special chars (@#%$^ etc.)\n" + "\n" +
                    "Does not contain space, tab, etc.")
    private String passwordAdmin;
    private String passwordAdminRipetuta;

}
