package com.iot.dashboard.service;

import com.iot.dashboard.model.Datastream;
import com.iot.dashboard.repository.DatastreamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatastreamService {

    private final DatastreamRepository repo;

    public DatastreamService(DatastreamRepository repo) {
        this.repo = repo;
    }

    public Datastream createOrUpdate(String templateId, String pin, String type, String value, String title, String configJson) {
        Datastream ds = repo.findByTemplateIdAndPin(templateId, pin)
                .orElse(new Datastream());
        ds.setTemplateId(templateId);
        ds.setPin(pin);
        ds.setType(type);
        ds.setValue(value);
        ds.setTitle(title);
        ds.setConfigJson(configJson);
        return repo.save(ds);
    }

    public List<Datastream> getAll(String templateId) {
        return repo.findByTemplateId(templateId);
    }

    public Datastream getOne(String templateId, String pin) {
        return repo.findByTemplateIdAndPin(templateId, pin)
                .orElseThrow(() -> new RuntimeException("Datastream not found"));
    }

    public void delete(String templateId, String pin) {
        Datastream ds = repo.findByTemplateIdAndPin(templateId, pin)
                .orElseThrow(() -> new RuntimeException("Datastream not found"));
        repo.delete(ds);
    }


    public Datastream updateValue(String templateId, String pin, String value) {
        Datastream ds = repo.findByTemplateIdAndPin(templateId, pin)
                .orElseThrow(() -> new RuntimeException("Datastream not found"));
        ds.setValue(value);
        return repo.save(ds);
    }

    public String getValue(String templateId, String pin) {
        return repo.findByTemplateIdAndPin(templateId, pin)
                .map(Datastream::getValue)
                .orElse("0");
    }

}
