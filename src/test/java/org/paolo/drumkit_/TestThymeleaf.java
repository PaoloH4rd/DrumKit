package org.paolo.drumkit_;

import org.junit.jupiter.api.Test;
import org.paolo.drumkit_.dto.request.BloccaUtenteRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TestThymeleaf {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/welcome"));
    }
    @Test
    public void testHomePage2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))
                .andExpect(MockMvcResultMatchers.view().name("home_page"));
    }


    @Test
    public void testRegistrazioneUtenteConThymeleaf() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/registraCliente")
                        .param("nome", "Paolo")
                        .param("cognome", "Rossi")
                        .param("email", "craft@paolo.it") // Corretto dall'errore "crat@fratm.com"
                        .param("password", "password123")
                        .param("passwordRipetuta", "password123")
                        .param("dataNascita", "1999-01-01")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Imposta il tipo di contenuto corretto
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Controlla redirect
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register")); // Verifica URL di redirect
    }

    @Test
    public void testRegistrazioneUtenteConErroreValidazione() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/registraCliente")
                        .param("nome", "Paolo")
                        .param("cognome", "Rossi")
                        .param("email", "crat@fratm.com")
                        .param("password", "short")
                        .param("passwordRipetuta", "short")
                        .param("dataNascita", "1999-01-01")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Controlla redirect
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register")) // Verifica URL di redirect
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.registrazioneUtenteDTO"));

    }

    @Test
    public void testRegistrazioneUtenteConErroreCredenziali() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/registraCliente")
                        .param("nome", "Paolo")
                        .param("cognome", "Rossi")
                        .param("email", "crat@fratm.com")
                        .param("password", "shortaaaaaaaaa")
                        .param("passwordRipetuta", "shaaaaaaaaaort")
                        .param("dataNascita", "1999-01-01")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"));

    }
    @Test
    public void testLoginAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/login") // Modifica l'endpoint in base alla tua configurazione
                        .param("email", "admin@mail.it") // Email dell'admin
                        .param("password", "Administrator") // Inserisci la password corretta
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Controlla il redirect
                .andExpect(MockMvcResultMatchers.redirectedUrl("/dashboard")); // Cambia l'URL di redirect secondo la tua logica
    }
    @Test
    public void testLoginAdminFallito() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/login")
                        .param("email", "admin@mail.it") // Email dell'admin
                        .param("password", "adAASDAFFFFAF")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error=true"));
    }


    @Test
    public void testCambiaPassword_ValidRequest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/cambiaPassword")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("vecchiaPassword","administrator")
                        .param("nuovaPassword","Administrator")
                        .param("nuovaPasswordRipetuta","Administrator" )
                        .sessionAttr("email", "admin@mail.it"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/dashboard/profilo/cambiaPassword"));
    }

    @Test
    public void testCambiaPassword_InvalidRequest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "admin@mail.it");
        // Simula un errore di validazione
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/cambiaPassword").session(session)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("vecchiaPassword", "")
                        .sessionAttr("email", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/dashboard/profilo/cambiaPassword"));
    }

    @Test
    public void testCambiaPassword_DatoNonValidoException() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/cambiaPassword").session(session)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("vecchiaPassword", "oldPassword")
                        .param("nuovaPassword", "newPassword")
                        .param("nuovaPasswordRipetuta", "newPassword")
                        .sessionAttr("email", "admin@mail.it"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/dashboard/profilo/cambiaPassword"));
    }

    @Test
    @WithMockUser(username = "admin@mail.it", roles = {"ADMIN"})
    public void testPannelloAdmin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "admin@mail.it");

        mockMvc.perform(MockMvcRequestBuilders.get("/pannelloAdmin")
                        .session(session)) // Usa la sessione mock
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("/dashboard/vedi_pannello_admin"));

    }

    @Test
    @WithMockUser(username = "admin@mail.it", roles = {"ADMIN"})
    public void testApprovaProdotto() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloAdmin/approvaProdotto").session(session)
                        .param("idProdotto", "1") // Cambia l'ID prodotto a seconda della tua logica
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Controlla redirect
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloAdmin?successMessage=true"));
    }

    @Test
    @WithMockUser(username = "a@mail.it", roles = {"ADMIN"})
    public void testRifiutaProdotto() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloAdmin/rifiutaProdotto").session(session)
                        .param("idProdotto", "1") // Cambia l'ID prodotto a seconda della tua logica
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloAdmin?successMessage=true"));
    }

    @Test
    @WithMockUser(username = "a@mail.it", roles = {"ADMIN"})
    public void testPannelloBlocchi() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "admin@mail.it");

        mockMvc.perform(MockMvcRequestBuilders.get("/pannelloAdmin/pannelloBlocchi").session(session))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/admin/pannello_blocchi")) ;
    }

    @Test
    @WithMockUser(username = "admin@mail.it", roles = {"ADMIN"})
    public void testBloccaUtente() throws Exception {
        MockHttpSession session = new MockHttpSession();
        //bloccautente dto
        BloccaUtenteRequestDTO bloccaUtenteRequestDTO = new BloccaUtenteRequestDTO();
        session.setAttribute("email", "admin@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloAdmin/bloccaUtente").session(session)
                        .param("email", "utente@mail.it") // Cambia l'email a seconda della tua logica
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloAdmin"));
    }

    @Test
    @WithMockUser(username = "admin@mail.it", roles = {"ADMIN"})
    public void testSbloccaUtente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/pannelloAdmin/sbloccaUtente")
                        .param("email", "utente@mail.it") // Cambia l'email a seconda della tua logica
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/pannelloAdmin"));
    }

    @Test
    @WithMockUser(username = "admin@mail.it", roles = {"ADMIN"})
    public void testProfiloVenditore() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pannelloAdmin/profiloVenditore")
                        .param("id", "10")) // Cambia l'ID a seconda della tua logica
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/admin/vedi_profilo_venditore_admin"));
    }

    @Test
    public void testLogout() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");
        // Esegui la richiesta di logout
        mockMvc.perform(MockMvcRequestBuilders.post("/logout")
                        .session(session)) // Aggiungi la sessione alla richiesta
                .andExpect(status().is3xxRedirection()) // Controlla che ci sia un redirect
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?logout")); // Verifica l'URL di redirect
    }
    @Test
    public void testPannelloCliente() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.get("/areaCliente").session(session))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/vedi_pannello_cliente"));
    }

}
