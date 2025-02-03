package org.paolo.drumkit_;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.paolo.drumkit_.controller.SuperAdminController;
import org.paolo.drumkit_.dto.request.RegistrazioneUtenteDTO;
import org.paolo.drumkit_.dto.response.AdminDisattivaSelectResponseDTO;
import org.paolo.drumkit_.exception.DatoNonValidoException;
import org.paolo.drumkit_.facade.UtenteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SuperAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteFacade utenteFacade;

    @BeforeEach
    void setUp() {
        when(utenteFacade.getAllActiveAdmins()).thenReturn(Collections.emptyList());
    }

    @Test
    public void testPannelloSAdmin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "super-admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.get("/pannelloSuperAdmin").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/dashboard/vedi_pannello_superadmin"));
    }

    @Test
    public void testDisattivaAdminSuccess() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "super-admin@mail.it");
        doNothing().when(utenteFacade).disattivaAdmin(200L);

        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloSuperAdmin/disattivaAdmin").session(session)
                        .param("adminId", "200"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloSuperAdmin"));
    }


    @Test
    public void testAggiungiAdminSuccess() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "super-admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloSuperAdmin/aggiungiAdmin").session(session)
                        .param("nome", "Paolo")
                        .param("cognome", "Rossi")
                        .param("email", "paolo@mail.com")
                        .param("password", "password123")
                        .param("passwordRipetuta", "password123")
                        .param("dataNascita", "1990-01-01"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloSuperAdmin"));
    }
    @Test
    public void testAggiungiAdminUnSuccess() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "super-admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloSuperAdmin/aggiungiAdmin").session(session)
                        .param("nome", "Paolo")
                        .param("cognome", "Rossi")
                        .param("email", "admin@mail.it")
                        .param("password", "administrator")
                        .param("passwordRipetuta", "Administartaodo")
                        .param("dataNascita", "1990-01-01")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloSuperAdmin"));
    }
    @Test
    public void testAggiungiAdminUnValid() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "super-admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloSuperAdmin/aggiungiAdmin").session(session)
                        .param("nome", "Paolo")
                        .param("cognome", "Rossi")
                        .param("email", "paol")
                        .param("password", "password123")
                        .param("passwordRipetuta", "password123")
                        .param("dataNascita", "1990-01-01")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloSuperAdmin"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.registrazioneUtenteDTO"));
    }
}
