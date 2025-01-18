package org.paolo.drumkit_;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TestThymeleaf {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testRegistrazioneUtenteConThymeleaf() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/registraCliente")
                        .param("nome", "Paolo")
                        .param("cognome", "Rossi")
                        .param("email", "crat@fratm.com")
                        .param("password", "password123")
                        .param("passwordRipetuta", "password123")
                        .param("dataNascita", "1999-01-01")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Form data
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Controlla redirect
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register?successMessage=true")) // Verifica URL di redirect
                .andExpect(MockMvcResultMatchers.flash().attribute("successMessage", "Registrazione avvenuta con successo")); // Verifica attributo flash
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
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))// Verifica redirect alla pagina del form
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.registrazioneUtenteDTO"));

    }


}
