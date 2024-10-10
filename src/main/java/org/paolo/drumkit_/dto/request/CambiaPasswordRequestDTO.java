package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// le request sono le info che ti invia il client per fare una richiesta
@Data
public class CambiaPasswordRequestDTO {

    private String vecchiaPassword;
    @Min(value = 1 , message ="id non valido")
    private long id;

    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$" ,
            message = "At least 8 chars\n" + "\n" +
                    "Contains at least one digit\n" + "\n" +
                    "Contains at least one lower alpha char and one upper alpha char\n" + "\n" +
                    "Contains at least one char within a set of special chars (@#%$^ etc.)\n" + "\n" +
                    "Does not contain space, tab, etc.")
    private String nuovaPassword;
    private String nuovaPasswordRipetuta;

}
