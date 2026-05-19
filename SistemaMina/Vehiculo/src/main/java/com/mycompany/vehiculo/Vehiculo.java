package com.mycompany.vehiculo;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Vehiculo {

    public static void main(String[] args) {
        String idVehiculo = System.getenv().getOrDefault("VEHICULO_ID", "camion-01");
        String mqttHost = System.getenv().getOrDefault("MQTT_HOST", "localhost");
        String mqttPort = System.getenv().getOrDefault("MQTT_PORT", "1883");
        String mqttUser = System.getenv().getOrDefault("MQTT_USER", "guest");
        String mqttPassword = System.getenv().getOrDefault("MQTT_PASSWORD", "guest");
        String publisherId = UUID.randomUUID().toString();

        try {
            MqttClient publisher = new MqttClient("tcp://" + mqttHost + ":" + mqttPort, publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            options.setUserName(mqttUser);
            options.setPassword(mqttPassword.toCharArray());

            publisher.connect(options);
            System.out.println("Vehiculo " + idVehiculo + " conectado a RabbitMQ.");

            while (true) {
                double latSimulada = 27.48 + (Math.random() * 0.005);
                double lonSimulada = -109.93 + (Math.random() * 0.005);

                String payload = String.format(
                        "{\"lat\": %.4f, \"lon\": %.4f, \"id\": \"%s\"}",
                        latSimulada,
                        lonSimulada,
                        idVehiculo);

                if (publisher.isConnected()) {
                    MqttMessage msgPosicion = new MqttMessage(payload.getBytes());
                    msgPosicion.setQos(0);
                    publisher.publish("mineria/vehiculos/posicion", msgPosicion);

                    MqttMessage msgPersistencia = new MqttMessage("hierro cargamento".getBytes());
                    msgPersistencia.setQos(1);

                    String topicPersistencia = "mina/vehiculos/descarga/" + idVehiculo;
                    publisher.publish(topicPersistencia, msgPersistencia);

                    System.out.println("Enviado -> Posicion: " + payload + " | Persistencia: " + topicPersistencia);
                } else {
                    System.out.println("Sin senal... posicion perdida.");
                }

                Thread.sleep(3000);
            }

        } catch (Exception e) {
            System.out.println("Error critico en el sistema del vehiculo: " + e.getMessage());
        }
    }
}
