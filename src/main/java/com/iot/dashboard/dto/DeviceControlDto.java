package com.iot.dashboard.dto;

public class DeviceControlDto {
    private String deviceId;
    private Long widgetId;
    private String value;

    // Getters and setters
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public Long getWidgetId() { return widgetId; }
    public void setWidgetId(Long widgetId) { this.widgetId = widgetId; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}