package io.pivotal.activities.builders;

import io.pivotal.activities.models.Destination;
import io.pivotal.activities.repositories.DestinationRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class DestinationBuilder {
    @Inject private DestinationRepository destinationRepository;
    Destination destination = new Destination();

    public DestinationBuilder withCity(String city) {
        destination.setCity(city);
        return this;
    }

    public Destination inMemory() {
        return destination;
    }
}