package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.RegistrazioneRequestDTO;
import org.paolo.drumkit_.dto.response.RegistrazioneResponseDTO;
import org.paolo.drumkit_.mapper.UtenteMapper;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UtenteFacade {
    private final UtenteService utenteService;
    private final UtenteMapper mapper;

    //chiama metodo come l'api
    public RegistrazioneResponseDTO registraCliente(RegistrazioneRequestDTO request){
        if (!request.getPassword().equals(request.getPasswordRipetuta())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Utente u = mapper.fromRegistrazioneRequestDTO(request);
        utenteService.add(u);

        return mapper.toRegistrazioneResponseDTO(u);
    }
}
