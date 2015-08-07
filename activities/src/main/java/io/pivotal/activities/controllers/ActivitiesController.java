package io.pivotal.activities.controllers;

import io.pivotal.activities.models.Activity;
import io.pivotal.activities.models.Destination;
import io.pivotal.activities.repositories.ActivityRepository;
import io.pivotal.activities.repositories.DestinationRepository;
import io.pivotal.activities.responses.ActivityResponse;
import io.pivotal.activities.serializers.ActivitySerializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ActivitiesController {
    @Inject private ActivityRepository activityRepository;
    @Inject private DestinationRepository destinationRepository;
    @Inject private ActivitySerializer activitySerializer;

    @RequestMapping("/destinations/{destinationId}/activities")
    public List<ActivityResponse> getActivitiesForDestination(@PathVariable Long destinationId) {
        List<Activity> activities = activityRepository.findByDestinationId(destinationId);
        Destination destination = destinationRepository.get(destinationId);

        return activitySerializer.serialize(activities, destination);
    }
}