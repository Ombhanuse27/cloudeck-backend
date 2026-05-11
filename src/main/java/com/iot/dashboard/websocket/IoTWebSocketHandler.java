// package com.iot.dashboard.websocket;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.iot.dashboard.service.MqttService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.*;
// import org.springframework.web.socket.handler.TextWebSocketHandler;

// import java.io.IOException;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;

// @Component
// public class IoTWebSocketHandler extends TextWebSocketHandler {

//     private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//     private final ObjectMapper mapper = new ObjectMapper();

//     private final MqttService mqttService;

//     @Autowired
//     public IoTWebSocketHandler(MqttService mqttService) {
//         this.mqttService = mqttService;
//     }

//     @Override
//     public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//         sessions.put(session.getId(), session);
//         System.out.println("✅ WebSocket Connected: " + session.getId());
//     }

//     @Override
//     public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//         String payload = message.getPayload();
//         System.out.println("📩 WebSocket Received: " + payload);

//         try {
//             JsonNode json = mapper.readTree(payload);
//             String type = json.has("type") ? json.get("type").asText() : "";
//             String templateId = json.has("templateId") ? json.get("templateId").asText() : "";
//             String pin = json.has("pin") ? json.get("pin").asText() : "";
//             String value = json.has("value") ? json.get("value").asText() : "";

//             if ("widget_update".equals(type) && !templateId.isEmpty() && !pin.isEmpty()) {
//                 String topic = "iot/" + templateId + "/" + pin;

//                 // 1️⃣ Publish update to MQTT so ESP32 receives it
//                 mqttService.publish(topic, value);

//                 // 2️⃣ Broadcast update to all WebSocket clients
//                 broadcast(payload);
//             }

//         } catch (Exception e) {
//             System.err.println("❌ Invalid JSON payload: " + payload);
//             e.printStackTrace();
//         }
//     }

//     @Override
//     public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//         sessions.remove(session.getId());
//         System.out.println("❌ WebSocket Disconnected: " + session.getId());
//     }

//     public void broadcast(String message) {
//         sessions.values().forEach(session -> {
//             try {
//                 if (session.isOpen()) session.sendMessage(new TextMessage(message));
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         });
//     }
// }

