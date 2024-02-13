package com.github.dsmiles.todos;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

/**
 * A client for interacting with a REST-ful API to manage Todos.
 * This client utilizes Spring's RestTemplate for making HTTP requests.
 * It communicates with the JSONPlaceholder API to fetch todos.
 */
@Component
public class TodosClient {
    private final RestTemplate restTemplate;

    /**
     * Constructs a new TodosClient.
     *
     * @param restTemplateBuilder a builder for creating RestTemplate instances.
     *                            It is used to set up the RestTemplate with specific configurations.
     */
    public TodosClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
            .rootUri("https://jsonplaceholder.typicode.com")
            .setConnectTimeout(Duration.ofSeconds(2))
            .setReadTimeout(Duration.ofSeconds(2))
            .build();
    }

    /**
     * Fetches all todos from the JSONPlaceholder API.
     *
     * @return a list of todos fetched from the API.
     */
    public List<Todo> fetchAllTodos() {
        return this.restTemplate
            .exchange("/todos", HttpMethod.GET, null, new ParameterizedTypeReference<List<Todo>>() {})
            .getBody();
    }
}
