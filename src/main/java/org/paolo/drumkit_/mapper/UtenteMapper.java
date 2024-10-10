package org.paolo.drumkit_.mapper;


import org.paolo.drumkit_.dto.request.RegistrazioneRequestDTO;
import org.paolo.drumkit_.dto.response.RegistrazioneResponseDTO;
import org.paolo.drumkit_.model.Ruolo;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Component;

@Component
public class UtenteMapper {
    public Utente fromRegistrazioneRequestDTO(RegistrazioneRequestDTO dto) {
        Utente u = new Utente();
        u.setNome(dto.getNome());
        u.setCognome(dto.getCognome());
        u.setEmail(dto.getEmail());
        u.setDataNascita(dto.getDataNascita());
        u.setPassword(dto.getPassword());
        u.setRuolo(Ruolo.CLIENTE);
        return u;
    }
    // Questo è il builder pattern
    public RegistrazioneResponseDTO toRegistrazioneResponseDTO(Utente utente) {
        return new RegistrazioneResponseDTO.UtenteBuilderDTO()
                .setNome(utente.getNome())
                .setCognome(utente.getCognome())
                .setEmail(utente.getEmail())
                .build();
    }
}