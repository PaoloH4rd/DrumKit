package org.paolo.drumkit_;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
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

    @Test
    public void testAggiungiProdottoVenditaForm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.get("/areaCliente/aggiungiProdottoVendita")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/cliente/aggiungi_prodotto_vendita"));
    }

    @Test
    public void testAggiungiProdottoVendita() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/areaCliente/aggiungiProdottoVendita").session(session)
                        .param("nome", "Prodotto Test")
                        .param("descrizione", "Descrizione del prodotto")
                        .param("prezzo", "9.99")
                        .param("quantita", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/areaCliente/aggiungiProdottoVendita"));
    }
    @Test
    public void testAggiungiProdottoVenditaConImmagine() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/areaCliente/aggiungiProdottoVendita").session(session)
                        .param("nome", "Prodotto Test")
                        .param("descrizione", "Descrizione del prodotto")
                        .param("prezzo", "9.99")
                        .param("quantita", "1")
                        .param("immagine", "immagine.jpg") // Assumi che l'immagine venga gestita correttamente
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/areaCliente/aggiungiProdottoVendita"));
    }
//
    @Test
    public void testAggiungiProdottoVenditaNonValid() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");
        mockMvc.perform(MockMvcRequestBuilders.post("/areaCliente/aggiungiProdottoVendita").session(session)
                        .param("nome", "Prodotto Test")
                        .param("descrizione", "Descrizione del prodotto")
                        .param("prezzo", "9.99")
                        .param("quantita", "1")
                        .param("immagine", "") // Assumi che l'immagine venga gestita correttamente
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/areaCliente/aggiungiProdottoVendita"));
    }

    @Test
    public void testAggiungiProdottoVenditaEntroTry2() throws Exception {
        // Crea un file multipart fittizio
        MockMultipartFile file = new MockMultipartFile(
                "immagine", // Nome del parametro nel controller
                "test.jpg", // Nome del file
                MediaType.IMAGE_JPEG_VALUE, // Tipo MIME
                "fake image content".getBytes() // Contenuto del file
        );
        byte[] files = file.getBytes();


        // Crea una sessione mock e imposta un attributo
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("email", "utente@mail.it");

        // Crea manualmente la richiesta multipart
        MockMultipartHttpServletRequestBuilder requestBuilder =
                (MockMultipartHttpServletRequestBuilder) MockMvcRequestBuilders.multipart("/areaCliente/aggiungiProdottoVendita")
                        .file("immagine", files) // Aggiungi il file
                        .param("nome", "Prodotto Test")
                        .param("descrizione", "Descrizione del prodotto")
                        .param("prezzo", "9.99")
                        .param("quantita", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Esegui la richiesta mock
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection()) // Verifica che ci sia un reindirizzamento
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?notLogged=true/")); // Verifica l'URL di reindirizzamento
    }


}
