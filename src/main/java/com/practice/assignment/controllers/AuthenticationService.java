package com.practice.assignment.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {

    @Value("${authentication.api.url}") 
    private String apiUrl;

    public String authenticate(String loginId, String password) {
        // Prepare the request body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body as per your API's requirements
        String requestBody = String.format("{\"login_id\": \"%s\", \"password\": \"%s\"}", loginId, password);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send a POST request to the authentication API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Check the response status
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Authentication successful, extract the token from the response
            String token = responseEntity.getBody();

            return token;
        } else {
            // Authentication failed
            return null;
        }
    }
}

