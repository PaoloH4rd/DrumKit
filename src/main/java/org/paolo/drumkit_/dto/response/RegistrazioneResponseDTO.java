package org.paolo.drumkit_.dto.response;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RegistrazioneResponseDTO {

    @NotBlank(message = "tutte le persone hanno un nome")
    private String nome;

    @NotBlank(message = "tutte le persone hanno un cognome")
    private String cognome;

    @NotBlank(message = "devi inserire una email")
    private String email;
    public RegistrazioneResponseDTO(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

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
