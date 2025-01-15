package org.paolo.drumkit_.service.impl;


import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Messaggio;
import org.paolo.drumkit_.repository.MessaggioRepository;
import org.paolo.drumkit_.service.def.MessaggioService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessaggioServiceImpl implements MessaggioService {

    private final MessaggioRepository repo;

    @Override
    public void aggiungiMessaggio(Messaggio m) {
        if(m.getChat()!=null) {
            repo.save(m);
        }
    }
}
