package org.paolo.drumkit_.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.service.def.CustomSenderMessaggioService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomSenderMessaggioServiceImpl implements CustomSenderMessaggioService {
    private final RabbitTemplate template;
    private final ObjectMapper mapper; // Inietta automaticamente il mapper

    @Override
    public void inviaNotifica(MessaggioResponseDTO m, String topic) {
        try {
            // Converte l'oggetto MessaggioResponseDTO in una stringa JSON
            String json = mapper.writeValueAsString(m);

            template.convertAndSend("ExchangeDurable", topic, json);
            System.out.println("Messaggio inviato con successo");
            //mostra  il json inviato
            System.out.println(json);
        } catch (JsonProcessingException e) {
            // Gestisce errori nella serializzazione del JSON
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore nella conversione del messaggio in JSON", e);
        }
    }
}
