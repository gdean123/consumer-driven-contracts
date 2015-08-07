package io.pivotal.activities.serializers;

import io.pivotal.activities.models.Activity;
import io.pivotal.activities.responses.ActivityResponse;
import io.pivotal.activities.models.Destination;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivitySerializer {
    public List<ActivityResponse> serialize(List<Activity> activities, Destination destination) {
        return activities.stream()
                .map(activity -> createActivityResponse(destination, activity))
                .collect(Collectors.toList());
    }

    private ActivityResponse createActivityResponse(Destination destination, Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setCity(destination.getCity());
        activityResponse.setName(activity.getName());
        return activityResponse;
    }
}
