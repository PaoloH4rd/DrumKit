package org.paolo.drumkit_.mapper;

import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Messaggio;
import org.paolo.drumkit_.model.Utente;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatMapper {
//    public List<String> toUsername(Utente u, List<Chat> chat){
//        List<String> utenti=new ArrayList<>();
//        for(Chat c:chat) {
//            if(c.getUtenteUno().getUsername().equalsIgnoreCase(u.getUsername())) {
//                utenti.add(c.getUtenteUno().getUsername());
//            }else utenti.add(c.getUtenteDue().getUsername());
//        }
//        return utenti;
//    }

    public List<MessaggioResponseDTO> toMessaggioResponseDTOList(Chat c){
        if(c==null||c.getMessaggi()==null)return new ArrayList<>();
        return c.getMessaggi().stream()
                .sorted((c1,c2)->c1.getDataOra().compareTo(c2.getDataOra()))
                .map(m->this.toMessaggioResponseDTO(c.getUtenteUno(),c.getUtenteDue(),m))
                .toList();

    }

    public MessaggioResponseDTO toMessaggioResponseDTO(Utente u1, Utente u2, Messaggio m) {
//        MessaggioResponseDTO messaggioResponseDTO=new MessaggioResponseDTO();
//        messaggioResponseDTO.setData(m.getDataOra().format(DateTimeFormatter.ofPattern("EEEE dd MMM yyyy hh:mm:ss")));
//        messaggioResponseDTO.setEmailMittente(m.isPrimoUtente()?u1.getUsername():u2.getUsername());
//        messaggioResponseDTO.setTesto(m.getTesto());
//        return messaggioResponseDTO;
        return new MessaggioResponseDTO.Builder()
                .setData(m.getDataOra().format(DateTimeFormatter.ofPattern("EEEE dd MMM yyyy hh:mm:ss")))
//                .setEmailDestinatario(m.isPrimoUtente()?u2.getUsername():u1.getUsername())
                .setEmailMittente(m.isPrimoUtente()?u1.getUsername():u2.getUsername())
                .setTesto(m.getTesto())
                .build();
    }
}
