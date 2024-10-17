package org.paolo.drumkit_.dto.response;

import lombok.Data;


@Data
public class RegistrazioneResponseDTO {
    private String nome;
    private String cognome;
    private String email;

    public RegistrazioneResponseDTO(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
    //serve un builder per ogni response dto
    public static class UtenteBuilderDTO {
        private String nome;
        private String cognome;
        private String email;


        public UtenteBuilderDTO setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public UtenteBuilderDTO setCognome(String cognome) {
            this.cognome = cognome;
            return this;
        }

        public UtenteBuilderDTO setEmail(String email) {
            this.email = email;
            return this;
        }


        //torna un response DTO con le cose che voglio io
        public RegistrazioneResponseDTO build() {
            return new RegistrazioneResponseDTO(nome, cognome, email);
        }
    }
}
