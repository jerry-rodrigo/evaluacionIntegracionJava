package com.desafioTecnico.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Prueba para obtener el precio por parámetros exitosamente.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    @Test
    public void getPriceByParameters_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/price/listar")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14-10:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("Jerry").password("123").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    /**
     * Prueba para realizar una solicitud a las 10 AM del día 14.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    @Test
    public void testRequestAt10AMOnDay14() throws Exception {
        String url = "/api/price/listar?brandId=1&productId=35455&applicationDate=2020-06-14-10:00:00";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("Jerry").password("123").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    /**
     * Prueba para realizar una solicitud a las 4 PM del día 14.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    @Test
    public void testRequestAt16PMOnDay14() throws Exception {
        String url = "/api/price/listar?brandId=1&productId=35455&applicationDate=2020-06-14-15:00:00";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("Jerry").password("123").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    /**
     * Prueba para realizar una solicitud a las 9 PM del día 14.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    @Test
    public void testRequestAt21PMOnDay14() throws Exception {
        String url = "/api/price/listar?brandId=1&productId=35455&applicationDate=2020-06-14-21:00:00";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("Jerry").password("123").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    /**
     * Prueba para realizar una solicitud a las 10 AM del día 15.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    @Test
    public void testRequestAt10AMOnDay15() throws Exception {
        String url = "/api/price/listar?brandId=1&productId=35455&applicationDate=2020-06-15-10:00:00";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("Jerry").password("123").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    /**
     * Prueba para realizar una solicitud a las 9 PM del día 16.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    @Test
    public void testRequestAt21PMOnDay16() throws Exception {
        String url = "/api/price/listar?brandId=1&productId=35455&applicationDate=2020-06-16-21:00:00";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("Jerry").password("123").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}