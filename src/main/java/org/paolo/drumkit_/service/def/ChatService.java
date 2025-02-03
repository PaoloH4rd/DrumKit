package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Utente;

import java.util.List;

public interface ChatService  {
    List<Chat> getAllByUsername(String username);
    Chat getByUsernameAndAltroNome(String username,String secondoUsername);
    Chat salva(Chat c);
}
