package com.mycompany.vehiculo;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Vehiculo {

    public static void main(String[] args) {
        String idVehiculo = "camion-01";
        // ID único para la conexión de red de este cliente
        String publisherId = UUID.randomUUID().toString();

        try {
            MqttClient publisher = new MqttClient("tcp://localhost:1883", publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true); // reconectar al perder conexión
            options.setCleanSession(true);       // olvidar sesiones viejas
            options.setConnectionTimeout(10);

            publisher.connect(options);
            System.out.println("Vehículo " + idVehiculo + " conectado a RabbitMQ.");

            // Bucle infinito
            while (true) {
                // Simulación de coordenadas
                double latSimulada = 27.48 + (Math.random() * 0.005);
                double lonSimulada = -109.93 + (Math.random() * 0.005);

                // Armamar el JSON
                String payload = String.format("{\"lat\": %.4f, \"lon\": %.4f, \"id\": \"%s\"}", 
                                                latSimulada, lonSimulada, idVehiculo);

                if (publisher.isConnected()) {
                    // 1. Mensaje de Telemetría (Tiempo real - QoS 0)
                    MqttMessage msgPosicion = new MqttMessage(payload.getBytes());
                    msgPosicion.setQos(0); 
                    publisher.publish("mineria/vehiculos/posicion", msgPosicion);
                    
                    // 2. Mensaje de Persistencia (Garantía de entrega - QoS 1)
                    MqttMessage msgPersistencia = new MqttMessage("hierro cargamento".getBytes());
                    msgPersistencia.setQos(1); // QoS 1 asegura "al menos una vez"
                    
                    String topicPersistencia = "mina/vehiculos/descarga/" + idVehiculo;
                    publisher.publish(topicPersistencia, msgPersistencia);

                    System.out.println("Enviado -> Posición: " + payload + " | Persistencia: " + topicPersistencia);
                } else {
                    System.out.println("Sin señal... posición perdida.");
                }

                Thread.sleep(3000);
            }

        } catch (Exception e) {
            System.out.println("Error crítico en el sistema del vehículo: " + e.getMessage());
        }
    }
}
