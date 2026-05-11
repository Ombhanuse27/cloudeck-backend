package com.iot.dashboard.repository;

import com.iot.dashboard.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByTemplateId(Long templateId);
    Optional<Device> findByDeviceId(String deviceId);  // ✅ updated
}
