package org.paolo.drumkit_.mapper;


import org.paolo.drumkit_.dto.response.UtenteDTO;

import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Component;


@Component
public class UtenteMapper {

    public UtenteDTO toUtenteDTO(Utente utente) {
        return new UtenteDTO.Builder()
                .setNome(utente.getNome())
                .setCognome(utente.getCognome())
                .setEmail(utente.getEmail())
                .setRuoli(utente.getAuthorities().stream()
                        .map(authority -> authority.getAuthority()) // Mappa i GrantedAuthority in String
                        .toList())
                .build();
    }
}