package org.paolo.drumkit_;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.paolo.drumkit_.controller.CarrelloController;
import org.paolo.drumkit_.facade.RigaOrdineFacade;
import org.paolo.drumkit_.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RigaOrdineFacade rigaOrdineFacade;

    @Mock
    private SecurityContext securityContext;

    @Test
    public void testPannelloCarrello() throws Exception {
        Utente utente = new Utente();
        utente.setId(1L);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(utente);
        when(rigaOrdineFacade.getProdottiCarrello(1L)).thenReturn(List.of());
        when(rigaOrdineFacade.getTotaleCarrello(1L)).thenReturn(100.0);

        mockMvc.perform(MockMvcRequestBuilders.get("/areaCliente/carrello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/cliente/vedi_carrello"));
    }

    @Test
    public void testConfermaCarrello() throws Exception {
        Utente utente = new Utente();
        utente.setId(1L);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(utente);

        mockMvc.perform(MockMvcRequestBuilders.post("/areaCliente/carrello/confermaCarrello"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/areaCliente/carrello"));
    }

    @Test
    public void testCambiaQuantita() throws Exception {
        Utente utente = new Utente();
        utente.setId(1L);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(utente);

        mockMvc.perform(MockMvcRequestBuilders.post("/areaCliente/carrello/cambiaQuantita")
                        .param("idProdotto", "1")
                        .param("quantita", "3"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/areaCliente/carrello"));
    }

    @Test
    public void testRimuoviProdotto() throws Exception {
        Utente utente = new Utente();
        utente.setId(1L);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(utente);

        mockMvc.perform(MockMvcRequestBuilders.post("/areaCliente/carrello/rimuoviProdotto")
                        .param("idProdotto", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/areaCliente/carrello"));
    }
}
