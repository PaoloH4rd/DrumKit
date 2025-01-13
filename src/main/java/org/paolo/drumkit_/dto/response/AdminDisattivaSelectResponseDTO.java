package org.paolo.drumkit_.dto.response;

import lombok.Data;
import org.paolo.drumkit_.exception.DatoNonValidoException;

@Data
public class AdminDisattivaSelectResponseDTO {
    private Long id;
    private String nome;
    private String email;

    private AdminDisattivaSelectResponseDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String email;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AdminDisattivaSelectResponseDTO build() {
            if (email != null && !email.isBlank()) {
                return new AdminDisattivaSelectResponseDTO(id, nome, email);
            } else {
                throw new DatoNonValidoException("L'email non è valida o mancante");
            }
        }
    }
}
