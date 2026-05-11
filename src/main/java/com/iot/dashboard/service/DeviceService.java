package com.iot.dashboard.service;

import com.iot.dashboard.dto.DeviceControlDto;
import com.iot.dashboard.model.Device;
import com.iot.dashboard.model.Template;
import com.iot.dashboard.repository.DeviceRepository;
import com.iot.dashboard.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private MqttService mqttService;

    /**
     * Send control commands to a device via MQTT
     */
    public void controlDevice(DeviceControlDto controlDto) {
        Device device = deviceRepository.findByDeviceId(controlDto.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Device not found"));

        String topic = "devices/" + device.getDeviceId() + "/control";
        String payload = String.format("{\"widgetId\":%d,\"value\":\"%s\"}",
                controlDto.getWidgetId(), controlDto.getValue());

        try {
            mqttService.publish(topic, payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send MQTT message", e);
        }
    }

    /**
     * Get all devices linked to a template
     */
    public List<Device> getTemplateDevices(String templateId) {
        Template template = templateRepository.findByTemplateId(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        return deviceRepository.findByTemplateId(template.getId());
    }

    /**
     * Get real-time or stored device data
     */
    public Map<String, Object> getDeviceData(String deviceId) {
        Device device = deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        // ⚡ For now returning mock data.
        // Later you can fetch real data from DB, MQTT retained messages, or time-series storage
        Map<String, Object> data = new HashMap<>();
        data.put("deviceId", device.getDeviceId());
        data.put("status", "ONLINE");
        data.put("lastUpdated", new Date());
        data.put("metrics", Map.of("temperature", 25.5, "humidity", 60));
        return data;
    }
}
