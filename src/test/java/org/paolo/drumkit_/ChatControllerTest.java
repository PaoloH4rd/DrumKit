package org.paolo.drumkit_;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.paolo.drumkit_.controller.ChatController;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.ChatFacade;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.paolo.drumkit_.model.Chat;
import org.paolo.drumkit_.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ChatController chatController;

    @Mock
    private ChatFacade chatFacade;

    @Mock
    private UtenteFacade utenteFacade;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Model model;






    @Test
    public void testGetChatRabbit() throws Exception {
        Utente utente = new Utente();
        utente.setId(1L);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(utente);
        when(utenteFacade.getNomeByEmail("test@mail.com")).thenReturn("Test User");
        when(chatFacade.getChat(anyLong(), anyString())).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/areaCliente/chatDashboard/apriChatRabbit")
                        .param("chatId", "1")
                        .param("email", "utente@mail.it"))
                .andExpect(MockMvcResultMatchers.view().name("dashboard/cliente/chats/chat_fragment"));
    }
}
