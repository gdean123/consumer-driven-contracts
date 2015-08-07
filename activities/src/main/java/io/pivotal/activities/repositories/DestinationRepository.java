package io.pivotal.activities.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.activities.models.Destination;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DestinationRepository {
    public Destination get(Long id) throws IOException {
        String response = Request.Get("http://localhost:4321/destinations/" + id).execute().returnContent().asString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(response);

        Destination destination = new Destination();
        destination.setCity(json.get("city").asText());

        return destination;
    }
}