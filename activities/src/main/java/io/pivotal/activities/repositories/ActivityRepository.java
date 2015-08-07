package io.pivotal.activities.repositories;

import java.util.List;

import io.pivotal.activities.models.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    List<Activity> findByDestinationId(Long destinationId);
}