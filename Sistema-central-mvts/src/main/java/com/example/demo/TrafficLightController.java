/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.*;
/**
 * 
 * @author lagar
 */
@RestController
@RequestMapping("/api/semaforo")
public class TrafficLightController {

    private final String BROKER = "tcp://broker.hivemq.com:1883";
    private final String TOPIC = "mina/semaforo/1/control";

    @GetMapping("/cambiar/{color}")
    public String cambiarColor(@PathVariable String color) {
        try {
            MqttClient client = new MqttClient(BROKER, MqttClient.generateClientId());
            client.connect();
            
            MqttMessage message = new MqttMessage(color.toUpperCase().getBytes());
            client.publish(TOPIC, message);
            
            client.disconnect();
            return "Orden enviada: Semáforo en " + color;
        } catch (Exception e) {
            return "Error al enviar orden: " + e.getMessage();
        }
    }
}