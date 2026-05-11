package com.iot.dashboard.service;

import com.iot.dashboard.websocket.IoTWebSocketHandler;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;


@Service
public class MqttService {

    @Autowired
    @Lazy
    private IoTWebSocketHandler webSocketHandler; // Inject WebSocket handler lazily

    private final String brokerUrl = "tcp://localhost:1883"; // Mosquitto broker
    private final String clientId = "SpringBootServer";
    private MqttClient client;

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);

            System.out.println("✅ Connected to MQTT broker");

            // Subscribe to all template topics dynamically
            client.subscribe("iot/+/+", (topic, msg) -> {
                String payload = new String(msg.getPayload());
                System.out.println("📩 MQTT Received: " + topic + " -> " + payload);

                // Forward the message to WebSocket clients
                webSocketHandler.broadcast(payload);
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Publish message to a topic
    public void publish(String topic, String payload) {
        try {
            if (client != null && client.isConnected()) {
                MqttMessage message = new MqttMessage(payload.getBytes());
                message.setQos(1);
                client.publish(topic, message);
                System.out.println("📤 MQTT Publish: " + topic + " -> " + payload);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

