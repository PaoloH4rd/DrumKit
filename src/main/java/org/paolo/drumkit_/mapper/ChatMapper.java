package org.paolo.drumkit_.mapper;

import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Messaggio;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ChatMapper {
    public List<MessaggioResponseDTO> toMessaggioResponseDTOList(Chat c){
        if(c==null||c.getMessaggi()==null)return new ArrayList<>();
        return c.getMessaggi().stream()
                .sorted((c1,c2)->c1.getDataOra().compareTo(c2.getDataOra()))
                .map(m->this.toMessaggioResponseDTOdb(c.getUtenteUno(),c.getUtenteDue(),m))
                .toList();

    }

    public MessaggioResponseDTO toMessaggioResponseDTOdb(Utente u1, Utente u2, Messaggio m) {
        return new MessaggioResponseDTO.Builder()
                .setData(m.getDataOra().format(DateTimeFormatter.ofPattern("EEEE dd MMM yyyy hh:mm:ss")))
                //se l'utente che invia il messaggio è l'utente loggat
                .setEmailDestinatario(m.isPrimoUtente()?u2.getUsername():u1.getUsername())
                .setEmailMittente(m.isPrimoUtente()?u1.getUsername():u2.getUsername())
                .setTesto(m.getTesto())
                .build();
    }
    public MessaggioResponseDTO toMessaggioResponseDTO(Utente u1, Utente u2, InviaMessaggioRequestDTO m) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMM yyyy hh:mm:ss a", Locale.ITALIAN);
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(m.getData(), DateTimeFormatter.ISO_DATE_TIME.withZone(zoneId));
        String formattedDate = zonedDateTime.format(formatter);
        return new MessaggioResponseDTO.Builder()
                .setData(formattedDate)
                .setEmailDestinatario(m.getEmailDestinatario())
                .setEmailMittente(m.getEmailMittente())
                .setTesto(m.getTesto())
                .build();
    }

}
