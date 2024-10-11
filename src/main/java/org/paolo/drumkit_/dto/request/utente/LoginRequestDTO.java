package org.paolo.drumkit_.dto.request.utente;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotNull(message = "utente e/o password non validi")
    @Email(message = "utente e/o password non validi")
    private String email;
    @Pattern(regexp =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message="utente e/o password non validi")

    private String password;
}
