package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Utente;

import java.util.List;

public interface ChatService  {
    List<Chat> getAllByUsername(String username);
    Chat getByUsernameAndAltroNome(String username,String secondoUsername);
    void creaChat(Utente utenteUno, Utente utenteDue);
    Chat salva(Chat c);
    Chat getById(Long id);
}
