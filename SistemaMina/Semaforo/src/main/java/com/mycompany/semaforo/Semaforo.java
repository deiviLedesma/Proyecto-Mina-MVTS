package com.mycompany.semaforo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Semaforo implements MqttCallback {

    private static final String MQTT_HOST = System.getenv().getOrDefault("MQTT_HOST", "localhost");
    private static final String MQTT_PORT = System.getenv().getOrDefault("MQTT_PORT", "1883");
    private static final String MQTT_USER = System.getenv().getOrDefault("MQTT_USER", "guest");
    private static final String MQTT_PASSWORD = System.getenv().getOrDefault("MQTT_PASSWORD", "guest");

    private final String idSemaforo = System.getenv().getOrDefault("SEMAFORO_ID", "01");
    private MqttClient client;

    public Semaforo() {
        try {
            String brokerUrl = "tcp://" + MQTT_HOST + ":" + MQTT_PORT;
            client = new MqttClient(brokerUrl, "Semaforo-" + idSemaforo);

            client.setCallback(this);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setUserName(MQTT_USER);
            options.setPassword(MQTT_PASSWORD.toCharArray());

            String topicEstado = "semaforos/" + idSemaforo + "/estado";
            options.setWill(topicEstado, "DESCONECTADO".getBytes(), 1, true);

            client.connect(options);
            System.out.println("Semaforo " + idSemaforo + " conectado a RabbitMQ.");

            client.subscribe("semaforos/" + idSemaforo + "/comandos", 1);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String comando = new String(message.getPayload());
        System.out.println("Orden recibida del servidor: " + comando);

        if (comando.equals("CAMBIAR_A_VERDE")) {
            System.out.println("Cambiando luz fisica a VERDE...");
            enviarEstado("VERDE");
        } else if (comando.equals("CAMBIAR_A_ROJO")) {
            System.out.println("Cambiando luz fisica a ROJO...");
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
        System.out.println("Senal perdida. El hardware intentara reconectar solo...");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public static void main(String[] args) {
        Semaforo semaforo = new Semaforo();
        semaforo.enviarEstado("ROJO");
    }
}
