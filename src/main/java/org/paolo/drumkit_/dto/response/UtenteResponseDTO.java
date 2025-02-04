package org.paolo.drumkit_.dto.response;

import lombok.Data;
import org.paolo.drumkit_.exception.DatoNonValidoException;

import java.util.List;

@Data

//sarebbe l'utente che mostro nel profilo
public class UtenteResponseDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String dataNascita;
    private List<String> ruoli; // Lista dei ruoli dell'utente

    private UtenteResponseDTO(Long id, String nome, String cognome, String email, String dataNascita, List<String> ruoli) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruoli = ruoli;
        this.dataNascita = dataNascita;
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String cognome;
        private String email;
        private String dataNascita;
        private List<String> ruoli; // Lista dei ruoli

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

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

        public Builder setDataNascita(String dataNascita) {
            this.dataNascita = dataNascita;
            return this;
        }

        public UtenteResponseDTO build() {
            if (email != null && !email.isBlank()) {
                return new UtenteResponseDTO(id, nome, cognome, email,dataNascita ,ruoli != null ? ruoli : List.of("NESSUN_RUOLO"));
            } else {
                throw new DatoNonValidoException("L'email non è valida o mancante");
            }
        }
    }
    public String getDataNascita() {
        //formatta la data di nascita in modo che sia leggibile e in ordine giorno/mese/anno
        String[] data = dataNascita.split("-");
        return data[2] + "/" + data[1] + "/" + data[0];
    }

    public String getRuoloPrincipale() {
        // Restituisce il primo ruolo o "NESSUN_RUOLO" se la lista è vuota
        //restituisce il primo ruolo senza trattino basso E SENZA ROLE_
        if (ruoli != null && !ruoli.isEmpty()) {
            return ruoli.get(0).replace("ROLE_", "").replace("_", " ");
        } else {
            return "NESSUN_RUOLO";
        }
    }
}
