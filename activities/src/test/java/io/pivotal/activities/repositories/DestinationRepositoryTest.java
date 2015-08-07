package io.pivotal.activities.repositories;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.model.PactFragment;
import io.pivotal.activities.models.Destination;
import io.pivotal.activities.supports.TestBase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class DestinationRepositoryTest extends TestBase {
    private DestinationRepository destinationRepository;

    @Before
    public void setup() {
        destinationRepository = new DestinationRepository();
    }

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("Destinations Service", "localhost", 4321, this);

    @Pact(provider = "Destinations Service", consumer = "Activities Service")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider defineContract) {
        return defineContract
            .given("a destination exists with id 456")
            .uponReceiving("a request for destination with id 456")
                .path("/destinations/456")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body("{ \"city\": \"Paris\" }").toFragment();
    }

    @Test
    @PactVerification("Destinations Service")
    public void get_fetchesTheDestinationWithTheGivenId() throws IOException {
        Destination destination = destinationRepository.get(456l);
        assertThat(destination.getCity()).isEqualTo("Paris");
    }
}