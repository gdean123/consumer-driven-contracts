package io.pivotal.activities.serializers;

import io.pivotal.activities.models.Activity;
import io.pivotal.activities.models.Destination;
import io.pivotal.activities.responses.ActivityResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ActivitySerializerTest {
    private ActivitySerializer activitySerializer;

    @Before
    public void setup() {
        activitySerializer = new ActivitySerializer();
    }

    @Test
    public void serialize_createsActivityResponsesFromActivities() {
        List<Activity> activities = createActivities("Skydiving", "Wine tasting");
        Destination destination = createDestination("Madrid");

        List<ActivityResponse> activityResponses = activitySerializer.serialize(activities, destination);

        assertThat(activityResponses.get(0).getCity()).isEqualTo("Madrid");
        assertThat(activityResponses.get(0).getName()).isEqualTo("Skydiving");

        assertThat(activityResponses.get(1).getCity()).isEqualTo("Madrid");
        assertThat(activityResponses.get(1).getName()).isEqualTo("Wine tasting");
    }

    private Destination createDestination(String city) {
        Destination destination = new Destination();
        destination.setCity(city);
        return destination;
    }

    private List<Activity> createActivities(String firstActivityName, String secondActivityName) {
        Activity firstActivity = new Activity();
        firstActivity.setName(firstActivityName);

        Activity secondActivity = new Activity();
        secondActivity.setName(secondActivityName);

        List<Activity> activities = new ArrayList<>();
        activities.add(firstActivity);
        activities.add(secondActivity);
        return activities;
    }
}