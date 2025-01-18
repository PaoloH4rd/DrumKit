package org.paolo.drumkit_.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class BloccaUtenteRequestDTO {
    @Email
    private String email;
}
