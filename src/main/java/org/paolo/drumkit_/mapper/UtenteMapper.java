package org.paolo.drumkit_.mapper;


import org.paolo.drumkit_.dto.response.AdminDisattivaSelectResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteResponseDTO;

import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;


@Component
public class UtenteMapper {

    public UtenteResponseDTO toUtenteDTO(Utente utente) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new UtenteResponseDTO.Builder()
                .setNome(utente.getNome())
                .setCognome(utente.getCognome())
                .setEmail(utente.getEmail())
                .setDataNascita(utente.getDataNascita() != null ? utente.getDataNascita().format(formatter) : null)
                .setRuoli(utente.getAuthorities().stream()
                        .map(authority -> authority.getAuthority()) // Mappa i GrantedAuthority in String
                        .toList())
                .build();
    }
    public AdminDisattivaSelectResponseDTO toAdminDTO(Utente utente) {
        return new AdminDisattivaSelectResponseDTO.Builder()
                .setId(utente.getId())
                .setNome(utente.getNome())
                .setEmail(utente.getEmail())
                .build();
    }

}