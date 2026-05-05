package com.mycompany.semaforo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Semaforo implements MqttCallback {

    private MqttClient client;
    private String idSemaforo = "01";

    public Semaforo() {
        try {
            // 1. Configuración inicial
            client = new MqttClient("tcp://localhost:1883", "Semaforo-" + idSemaforo);

            client.setCallback(this); 

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);

            // RabbitMQ publicará "DESCONECTADO" automáticamente si pierde seña.
            String topicEstado = "semaforos/" + idSemaforo + "/estado";
            options.setWill(topicEstado, "DESCONECTADO".getBytes(), 1, true);

            client.connect(options);
            System.out.println("Semáforo " + idSemaforo + " conectado a RabbitMQ.");

            client.subscribe("semaforos/" + idSemaforo + "/comandos", 1);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String comando = new String(message.getPayload());
        System.out.println("Orden recibida del servidor: " + comando);

        // Lógica del semáforo
        if (comando.equals("CAMBIAR_A_VERDE")) {
            System.out.println("Cambiando luz física a VERDE...");
            enviarEstado("VERDE"); // Avisamos al servidor que ya cambiamos
        } else if (comando.equals("CAMBIAR_A_ROJO")) {
            System.out.println("Cambiando luz física a ROJO...");
            enviarEstado("ROJO");
        }
    }

    public void enviarEstado(String estado) {
        try {
            MqttMessage mensaje = new MqttMessage(estado.getBytes());
            mensaje.setQos(1); 
            mensaje.setRetained(true);

            String topicEstado = "semaforos/" + idSemaforo + "/estado";
            
            client.publish(topicEstado, mensaje);
            System.out.println("Estado actualizado enviado a RabbitMQ: " + estado);
            
        } catch (MqttException e) {
            System.out.println("Error al intentar enviar el estado al servidor.");
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("¡Señal perdida! El hardware intentará reconectar solo...");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Se ejecuta cuando se recibe mensaje
    }

    public static void main(String[] args) {
        Semaforo semaforo = new Semaforo();

        semaforo.enviarEstado("ROJO");
    }
}
