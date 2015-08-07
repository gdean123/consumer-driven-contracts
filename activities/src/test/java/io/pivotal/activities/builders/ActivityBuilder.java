package io.pivotal.activities.builders;

import io.pivotal.activities.models.Activity;
import io.pivotal.activities.repositories.ActivityRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ActivityBuilder {
    @Inject private ActivityRepository activityRepository;
    private Activity activity = new Activity();

    public ActivityBuilder withDestinationId(Long destinationId) {
        activity.setDestinationId(destinationId);
        return this;
    }

    public ActivityBuilder withName(String name) {
        activity.setName(name);
        return this;
    }

    public Activity inTheDatabase() {
        return activityRepository.save(activity);
    }
}