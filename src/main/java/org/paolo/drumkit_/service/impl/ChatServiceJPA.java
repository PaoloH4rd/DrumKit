package org.paolo.drumkit_.service.impl;

import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Utente;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.repository.ChatRepository;
import org.paolo.drumkit_.service.def.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatServiceJPA implements ChatService {

    private final ChatRepository repo;

    @Override
    public List<Chat> getByCliente_IdAndIsChiusaIsfalse(Utente u) {
        return repo.findAllByCliente_IdAndIsChiusaIsFalse(u.getId());
    }

    @Override
    public void add(Chat chat) {
        repo.save(chat);
    }

    @Override
    public void update(Chat chat) {
        Chat c = new Chat();
        c.setId(chat.getId());
        c.setProdotto(chat.getProdotto());
        c.setAdmin(chat.getAdmin());
        c.setCliente(chat.getCliente());
        c.setMessaggi(chat.getMessaggi());
        repo.save(c);
    }

    @Override
    public Chat getById(long id) {
        return repo.findByIdAndIsChiusaIsFalse(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Chat> getAll() {
        return repo.findAllByIsChiusaIsFalse();
    }

    @Override
    public void setDisattivatoTrue(long id) {
        Chat c = getById(id);
        c.setChiusa(true);
        repo.save(c);
    }
}
