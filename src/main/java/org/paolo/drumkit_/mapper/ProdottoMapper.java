package org.paolo.drumkit_.mapper;

import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteVenditoreResponseDTO;
import org.paolo.drumkit_.model.Prodotto;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Component;

@Component
public class ProdottoMapper {
    public ProdottoInVenditaResponseDTO toProdottoInVenditaResponseDTO(Prodotto prodotto) {
        return new ProdottoInVenditaResponseDTO.Builder()
                .setId(prodotto.getId())
                .setNome(prodotto.getNome())
                .setDescrizione(prodotto.getDescrizione())
                .setPrezzo(prodotto.getPrezzo())
                .setQuantita(prodotto.getQuantita())
                .setImmagine(prodotto.getImmagine())
                .setVenditore(new UtenteVenditoreResponseDTO.Builder()
                        .setId(prodotto.getProprietario().getId())
                        .setNome(prodotto.getProprietario().getNome())
                        .setCognome(prodotto.getProprietario().getCognome())
                        .setEmail(prodotto.getProprietario().getEmail())
                        .build())
                .build();
    }
    public UtenteVenditoreResponseDTO toUtenteVenditoreResponseDTO(Utente utente) {
        return new UtenteVenditoreResponseDTO.Builder()
                .setId(utente.getId())
                .setNome(utente.getNome())
                .setCognome(utente.getCognome())
                .setEmail(utente.getEmail())
                .build();
    }
}
