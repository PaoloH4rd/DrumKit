package org.paolo.drumkit_;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.paolo.drumkit_.controller.ChatSocketController;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.facade.ChatFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatSocketControllerTest {

    @InjectMocks
    private ChatSocketController chatSocketController;

    @Mock
    private ChatFacade chatFacade;

    @Mock
    private UtenteFacade utenteFacade;

    @Test
    public void testSendMessage() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");
        Long chatId = 1L;
        InviaMessaggioRequestDTO messaggio = new InviaMessaggioRequestDTO();
        messaggio.setEmailMittente("utente@mail.it");
        messaggio.setTesto("Ciao");
        messaggio.setEmailDestinatario("utente2@mail.it");
        chatSocketController.sendMessage(chatId, messaggio);
    }
}
