package com.practice.assignment.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CustomerService {

    @Value("${authentication.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean addCustomer(Customer customer, String token) {
        // Prepare the request headers with the Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request entity with the customer data and headers
        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);

        // Build the URL with the query parameter
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("cmd", "create");

        // Send the POST request to add a customer
        ResponseEntity<Void> response = restTemplate.exchange(
                builder + "?cmd=create",
                HttpMethod.POST,
                requestEntity,
                Void.class);

        // Check if the API request was successful (e.g., HTTP status code 201)
        return response.getStatusCode() == HttpStatus.CREATED;
    }

    public List<Customer> getCustomerList(String token) {
        // Prepare the request headers with the Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create the request entity with headers
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Build the URL with the query parameter
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("cmd", "get_customer_list");

        // Send the GET request to retrieve the list of customers
        ResponseEntity<Customer[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Customer[].class);

        // Check if the API request was successful (e.g., HTTP status code 200)
        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            // Handle the case where the API request failed
            return Collections.emptyList();
        }
    }
}
