package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.mapper.ChatMapper;
import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Messaggio;
import org.paolo.drumkit_.model.Ruolo;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.service.def.ChatService;
import org.paolo.drumkit_.service.def.CustomSenderMessaggioService;
import org.paolo.drumkit_.service.def.MessaggioService;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatFacade {
    private final UtenteService utenteService;
    private final MessaggioService messaggioService;
    private final ChatService chatService;
    private final CustomSenderMessaggioService customSenderMessaggioService;
    private final ChatMapper chatMapper;
    public void inviaMessaggio(long idUser, InviaMessaggioRequestDTO inviaMessaggioRequestDTO) {
        Utente u1=utenteService.getById(idUser);
        Utente u2=utenteService.getByEmailforChat(inviaMessaggioRequestDTO.getEmailDestinatario());

        //controllo che l'email corrissponda ad un utente esistente
        if(u2==null || u2.getRuolo().equals(Ruolo.SUPER_ADMIN))throw new DatoNonValidoException("Utente non trovato");
        if (u1.isDisattivato()|| u2.isDisattivato()) throw new DatoNonValidoException("Utente disattivato");
        if(u1.getId()==u2.getId())throw new DatoNonValidoException("Non puoi inviarti un messaggio");
        Chat c=chatService.getByUsernameAndAltroNome(u1.getUsername(), u2.getUsername());

        if(c==null) {
            c=new Chat();
            c.setUtenteDue(u2);
            c.setUtenteUno(u1);
            c=chatService.salva(c);
        }
        Messaggio m=new Messaggio();
        m.setChat(c);
        m.setTesto(inviaMessaggioRequestDTO.getTesto());
        //se l'utente che invia il messaggio è l'utente loggatto
        m.setPrimoUtente(c.getUtenteUno().getUsername().equals(u1.getUsername()));

        messaggioService.aggiungiMessaggio(m);
        MessaggioResponseDTO mDTO=chatMapper.toMessaggioResponseDTO(u1, u2, inviaMessaggioRequestDTO);
        //invia il messaggio a rabbit solo se il message mapping ha chiamato questo metodo
        customSenderMessaggioService.inviaNotifica(mDTO, String.valueOf(c.getId()));
    }

    public List<MessaggioResponseDTO> getChat(long id, String email) {
        Utente u= utenteService.getById(id);
        Chat c=chatService.getByUsernameAndAltroNome(u.getUsername(), email);
        return chatMapper.toMessaggioResponseDTOList(c);
    }
    public List<Chat> getChats(long id) {
        Utente u= utenteService.getById(id);
        return chatService.getAllByUsername(u.getUsername());
    }

}
