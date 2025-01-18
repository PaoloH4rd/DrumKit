package org.paolo.drumkit_;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.paolo.drumkit_.dto.request.RegistrazioneUtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG, printOnlyOnFailure = false)
public class TestClass {
    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
//    @WithUserDetails("admn@mail.it")
    public void test1() throws Exception {
        RegistrazioneUtenteDTO dto = new RegistrazioneUtenteDTO();
        dto.setNome("Paolo");
        dto.setCognome("Rossi");
        dto.setEmail("crat@fratm.com");
        dto.setPassword("password");
        dto.setPasswordRipetuta("passwoasard");
        dto.setDataNascita("1999-01-01");
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/registraCliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testRegistrazioneUtente() throws Exception {
        RegistrazioneUtenteDTO dto = new RegistrazioneUtenteDTO();
        dto.setNome("Paolo");
        dto.setCognome("Rossi");
        dto.setEmail("crat@fratm.com");
        dto.setPassword("password123");
        dto.setPasswordRipetuta("password123");
        dto.setDataNascita("1999-01-01");
        String json = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/all/utente/registraCliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Verifica codice 200
        System.out.println(json);
    }



}
