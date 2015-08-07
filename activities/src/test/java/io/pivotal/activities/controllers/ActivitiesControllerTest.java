package io.pivotal.activities.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.pivotal.activities.builders.ActivityBuilder;
import io.pivotal.activities.builders.DestinationBuilder;
import io.pivotal.activities.models.Destination;
import io.pivotal.activities.repositories.DestinationRepository;
import io.pivotal.activities.supports.ControllerTestBase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ActivitiesControllerTest extends ControllerTestBase {
    @Mock private DestinationRepository destinationRepository;
    @Inject @InjectMocks private ActivitiesController activitiesController;
    @Inject ActivityBuilder createActivity;
    @Inject DestinationBuilder createDestination;

    @Test
    public void getActivitiesForDestination_returnsAllActivitiesForAGivenDestination() throws Exception {
        createActivity.withDestinationId(123l).withName("Skydiving").inTheDatabase();
        Destination destination = createDestination.withCity("Madrid").inMemory();
        when(destinationRepository.get(123l)).thenReturn(destination);

        JsonNode json = get("/destinations/123/activities").get(0);

        assertThat(json.get("name").asText()).isEqualTo("Skydiving");
        assertThat(json.get("city").asText()).isEqualTo("Madrid");
    }
}