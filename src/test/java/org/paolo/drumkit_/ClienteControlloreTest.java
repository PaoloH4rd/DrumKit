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
public class ClienteControlloreTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPannelloCliente() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");

        mockMvc.perform(MockMvcRequestBuilders.get("/areaCliente").session(session))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/vedi_pannello_cliente"));
    }

//    @Test
//    @Order(2)
//    public void testAggiungiProdottoVenditaForm() throws Exception {
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("email", "utente@mail.it");
//        mockMvc.perform(MockMvcRequestBuilders.get("/areaCliente/aggiungiProdottoVendita")
//                .session(session))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(model().attributeExists("prodottoRequestDTO"))
//                .andExpect(model().attribute("nome", "Paolo"));
//    }
//
//    @Test
//    @Order(3)
//    @WithMockUser // Simula un utente autenticato
//    public void testAggiungiProdottoVendita() throws Exception {
//        mockMvc.perform(post("/areaCliente/aggiungiProdottoVendita")
//                        .param("nome", "Prodotto Test")
//                        .param("descrizione", "Descrizione del prodotto")
//                        .param("prezzo", "99.99")
//                        .param("quantita", "10")
//                        .param("immagine", "")) // Assumi che l'immagine venga gestita correttamente
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/areaCliente/aggiungiProdottoVendita?successMessage=true"));
//    }
//
//    @Test
//    @Order(4)
//    public void testAggiungiProdottoVenditaConErrori() throws Exception {
//        mockMvc.perform(post("/areaCliente/aggiungiProdottoVendita")
//                        .param("nome", "")
//                        .param("descrizione", "")
//                        .param("prezzo", "")
//                        .param("quantita", "0"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/areaCliente/aggiungiProdottoVendita?errorMessage=true"));
//    }

    // Altri test per il controller possono essere aggiunti qui
}
