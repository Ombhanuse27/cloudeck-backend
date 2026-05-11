package com.iot.dashboard.controller;

import com.iot.dashboard.dto.DeviceControlDto;
import com.iot.dashboard.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/control")
    public ResponseEntity<?> controlDevice(@RequestBody DeviceControlDto controlDto) {
        deviceService.controlDevice(controlDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{deviceId}/data")
    public ResponseEntity<?> getDeviceData(@PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceData(deviceId));
    }

    @GetMapping("/{templateId}/devices")
    public ResponseEntity<?> getTemplateDevices(@PathVariable String templateId) {
        return ResponseEntity.ok(deviceService.getTemplateDevices(templateId));
    }
}