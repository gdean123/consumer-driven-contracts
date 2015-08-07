package io.pivotal.activities.builders;

import io.pivotal.activities.models.Destination;
import org.springframework.stereotype.Component;

@Component
public class DestinationBuilder {
    Destination destination = new Destination();

    public DestinationBuilder withCity(String city) {
        destination.setCity(city);
        return this;
    }

    public Destination inMemory() {
        return destination;
    }
}