package org.paolo.drumkit_.dto.response;

import lombok.Data;
import org.paolo.drumkit_.exception.DatoNonValidoException;
@Data
public class ProdottoInVenditaResponseDTO {
    private Long id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private int quantita;
    private UtenteVenditoreResponseDTO venditore;

    // Costruttore privato
    private ProdottoInVenditaResponseDTO( Long id,String nome, String descrizione, Double prezzo,
                                          int quantita, UtenteVenditoreResponseDTO venditore) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.venditore = venditore;
    }

    // Builder interno
    public static class Builder {
        private Long id;
        private String nome;
        private String descrizione;
        private Double prezzo;
        private int quantita;
        private UtenteVenditoreResponseDTO venditore;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setDescrizione(String descrizione) {
            this.descrizione = descrizione;
            return this;
        }

        public Builder setPrezzo(Double prezzo) {
            this.prezzo = prezzo;
            return this;
        }
        //quantita
        public Builder setQuantita(int quantita) {
            this.quantita = quantita;
            return this;
        }
        public Builder setVenditore(UtenteVenditoreResponseDTO venditore) {
            this.venditore = venditore;
            return this;
        }

        public ProdottoInVenditaResponseDTO build() {
            if (nome == null || nome.isBlank()) {
                throw new DatoNonValidoException("Il nome del prodotto non è valido o è mancante");
            }
            if (prezzo == null || prezzo < 0) {
                throw new DatoNonValidoException("Il prezzo non è valido o è mancante");
            }
            return new ProdottoInVenditaResponseDTO(id, nome, descrizione, prezzo, quantita, venditore);
        }
    }

}
