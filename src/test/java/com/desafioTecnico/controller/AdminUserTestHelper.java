package com.desafioTecnico.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AdminUserTestHelper {
    public static void createAdminUser() {
        String username = "jerry";
        String password = "123";

        RestTemplate restTemplate = new RestTemplate();

        // Configurar los encabezados
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Configurar los datos del usuario a registrar
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", password);

        // Crear una entidad HTTP con los encabezados y el cuerpo de la solicitud
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Realizar la solicitud POST para registrar el usuario administrador
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/auth/registerAdm",
                HttpMethod.POST,
                requestEntity,
                String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Usuario administrador creado exitosamente.");
        } else {
            System.out.println("Error al crear el usuario administrador: " + responseEntity.getBody());
        }
    }
}
