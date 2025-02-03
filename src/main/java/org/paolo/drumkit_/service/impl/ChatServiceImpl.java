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
public class ChatServiceImpl implements ChatService {

    private final ChatRepository repo;
    @Override
    public List<Chat> getAllByUsername(String username) {
        return repo.findAllByEmail(username);
    }

    @Override
    public Chat getByUsernameAndAltroNome(String username, String secondoUsername) {
        Chat c=repo.findAllByEmail(username, secondoUsername).orElse(null);
        return c;
    }

    @Override
    public Chat salva(Chat c) {
        return repo.save(c);
    }

}
