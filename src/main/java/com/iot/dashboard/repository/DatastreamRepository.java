package com.iot.dashboard.repository;

import com.iot.dashboard.model.Datastream;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DatastreamRepository extends JpaRepository<Datastream, Long> {

    // Get all datastreams for a template
    List<Datastream> findByTemplateId(String templateId);

    // Get a specific datastream by template and pin
    Optional<Datastream> findByTemplateIdAndPin(String templateId, String pin);
}
