package org.paolo.drumkit_;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.paolo.drumkit_.controller.DashboardController;
import org.paolo.drumkit_.dto.response.UtenteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDashboard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("vedi_dashboard"));
    }

    @Test
    public void testGetUserProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard/profilo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/vedi_profilo"));
    }

    @Test
    public void testMostraFormCambiaPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard/profilo/cambiaPassword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("dashboard/profilo/cambia_password"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cambiaPasswordRequest"));
    }
}
