package com.iot.dashboard.controller;

import com.iot.dashboard.model.Datastream;
import com.iot.dashboard.service.DatastreamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/datastreams")
public class DatastreamController {

    private final DatastreamService service;

    public DatastreamController(DatastreamService service) {
        this.service = service;
    }

    // Get all datastreams for a template (used when loading dashboard)
    @GetMapping("/{templateId}")
    public List<Datastream> list(@PathVariable String templateId) {
        return service.getAll(templateId);
    }

    // Get single datastream (used for polling)
    @GetMapping("/{templateId}/{pin}")
    public Datastream get(@PathVariable String templateId, @PathVariable String pin) {
        return service.getOne(templateId, pin);
    }

    // Create or update a datastream (called when widget first created)
    @PostMapping("/{templateId}/{pin}")
    public Datastream update(
            @PathVariable String templateId,
            @PathVariable String pin,
            @RequestParam String type,
            @RequestParam String value,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String configJson) {
        return service.createOrUpdate(templateId, pin, type, value, title, configJson);
    }

    // ✅ NEW: Update only the "value" (device/slider updates)
    @PostMapping("/{templateId}/{pin}/value")
    public Datastream updateValue(
            @PathVariable String templateId,
            @PathVariable String pin,
            @RequestBody Map<String, Object> payload) {

        String value = String.valueOf(payload.get("value"));
        return service.updateValue(templateId, pin, value);
    }


    // ✅ NEW: Fetch only the value (lightweight for frontend polling)
    @GetMapping("/{templateId}/{pin}/value")
    public String getValue(@PathVariable String templateId, @PathVariable String pin) {
        return service.getValue(templateId, pin);
    }

    // Delete a datastream
    @DeleteMapping("/{templateId}/{pin}")
    public void delete(@PathVariable String templateId, @PathVariable String pin) {
        service.delete(templateId, pin);
    }
}
