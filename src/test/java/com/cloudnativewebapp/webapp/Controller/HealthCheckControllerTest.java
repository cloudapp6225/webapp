package com.cloudnativewebapp.webapp.Controller;

import com.cloudnativewebapp.webapp.Service.HealthCheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(HealthCheckController.class)
class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthCheckService healthCheckService;

    @BeforeEach
    void setUp() {
        Mockito.when(healthCheckService.isDBConnected()).thenReturn(true);
    }

    @Test
    void checkDBHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/healthz"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/healthz?1=1"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void putDBHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/healthz"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void postDBHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/healthz"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void deleteDBHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/healthz"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}