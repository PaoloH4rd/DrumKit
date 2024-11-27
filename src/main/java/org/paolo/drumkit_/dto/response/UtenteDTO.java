package org.paolo.drumkit_.dto.response;

import lombok.Data;
import org.paolo.drumkit_.exception.DatoNonValidoException;


@Data
public class UtenteDTO {
    private String nome;
    private String cognome;
    private String email;

    private UtenteDTO(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public static class Builder {
        private String nome;
        private String cognome;
        private String email;

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }
        public Builder setCognome(String cognome) {
            this.nome = nome;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UtenteDTO build(){
            if(email!=null)return new UtenteDTO(nome,cognome,email);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }
}
