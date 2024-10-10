package org.paolo.drumkit_.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.RegistrazioneRequestDTO;
import org.paolo.drumkit_.dto.response.RegistrazioneResponseDTO;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.service.def.UtenteService;
import org.paolo.drumkit_.service.impl.UtenteServiceJPA;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService service;
    private final UtenteFacade facade;

    @PostMapping("/registraCliente")
    public ResponseEntity<RegistrazioneResponseDTO> registraCliente (@Valid @RequestBody RegistrazioneRequestDTO request) {
        RegistrazioneResponseDTO registrazioneResponseDTO= facade.registraCliente(request);

        return ResponseEntity.ok(registrazioneResponseDTO);
    }
}