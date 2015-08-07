package io.pivotal.activities.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import io.pivotal.activities.models.Destination;
import io.pivotal.activities.utilities.Http;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DestinationRepository {
    public Destination get(Long id) throws IOException {
        JsonNode json = Http.get("http://localhost:4321/destinations/" + id);

        Destination destination = new Destination();
        destination.setCity(json.get("city").asText());

        return destination;
    }
}