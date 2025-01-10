package org.paolo.drumkit_.dto.response;

import lombok.Data;
import org.paolo.drumkit_.exception.DatoNonValidoException;

import java.util.List;

@Data
public class UtenteDTO {
    private String nome;
    private String cognome;
    private String email;
    private List<String> ruoli; // Lista dei ruoli dell'utente

    private UtenteDTO(String nome, String cognome, String email, List<String> ruoli) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruoli = ruoli;
    }

    public static class Builder {
        private String nome;
        private String cognome;
        private String email;
        private List<String> ruoli; // Lista dei ruoli

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setCognome(String cognome) {
            this.cognome = cognome; // Corretto errore di assegnazione
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRuoli(List<String> ruoli) {
            this.ruoli = ruoli; // Corretto per assegnare la lista dei ruoli
            return this;
        }

        public UtenteDTO build() {
            if (email != null && !email.isBlank()) {
                return new UtenteDTO(nome, cognome, email, ruoli != null ? ruoli : List.of("NESSUN_RUOLO"));
            } else {
                throw new DatoNonValidoException("L'email non è valida o mancante");
            }
        }
    }

    public String getRuoloPrincipale() {
        // Restituisce il primo ruolo o "NESSUN_RUOLO" se la lista è vuota
        return ruoli != null && !ruoli.isEmpty() ? ruoli.get(0) : "NESSUN_RUOLO";
    }
}
