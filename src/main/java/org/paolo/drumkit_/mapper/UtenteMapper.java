package org.paolo.drumkit_.mapper;


import org.paolo.drumkit_.dto.response.UtenteDTO;

import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Component;


@Component
public class UtenteMapper {

    public UtenteDTO toUtenteDTO(Utente utente) {
        //cosa vuoi che veda l'utente quando vede il suo profilo
        UtenteDTO.Builder uDTO = new UtenteDTO.Builder()
                .setNome(utente.getNome())
                .setCognome(utente.getCognome())
        .setEmail(utente.getEmail());

        return uDTO.build();
    }
}