package io.pivotal.activities.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import io.pivotal.activities.models.Destination;
import io.pivotal.activities.utilities.Json;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DestinationRepository {
    public Destination get(Long id) throws IOException {
        String response = Request.Get("http://localhost:4321/destinations/" + id).execute().returnContent().asString();

        JsonNode json = Json.parse(response);

        Destination destination = new Destination();
        destination.setCity(json.get("city").asText());

        return destination;
    }
}