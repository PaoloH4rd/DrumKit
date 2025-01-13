package org.paolo.drumkit_.dto.response;

import lombok.Data;

@Data
public class UtenteVenditoreResponseDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;

    private UtenteVenditoreResponseDTO(Long id, String nome, String cognome, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String cognome;
        private String email;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setCognome(String cognome) {
            this.cognome = cognome;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UtenteVenditoreResponseDTO build() {
            return new UtenteVenditoreResponseDTO(id, nome, cognome, email);
        }
    }
}
