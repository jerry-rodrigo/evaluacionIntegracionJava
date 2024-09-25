package com.desafioTecnico.Aceptacion;

import com.desafioTecnico.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerAuthTestAcceptance {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception {
        DtoRegister dtoRegister = new DtoRegister();
        dtoRegister.setUsername("testuser");
        dtoRegister.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(dtoRegister)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Registro de usuario exitoso"));
    }

    @Test
    public void testRegisterAdm() throws Exception {
        DtoRegister dtoRegister = new DtoRegister();
        dtoRegister.setUsername("adminuser");
        dtoRegister.setPassword("adminpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registerAdm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(dtoRegister)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Registro de admin exitoso"));
    }

    @Test
    public void testLogin() throws Exception {
        DtoLogin dtoLogin = new DtoLogin();
        dtoLogin.setUsername("testuser");
        dtoLogin.setPassword("password");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(dtoLogin)))
                .andExpect(status().isUnauthorized());

        // Validar que se devuelve un token JWT en la respuesta
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response content: " + responseContent);
        DtoAuthResponse authResponse = new DtoAuthResponse("myAccessToken");
        String accessToken = authResponse.getAccessToken(); // Accediendo directamente al campo accessToken

        assertNotNull(accessToken);
    }
}
