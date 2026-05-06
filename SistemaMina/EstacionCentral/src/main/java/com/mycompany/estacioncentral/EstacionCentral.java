package com.mycompany.estacioncentral;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class EstacionCentral {

    private static final String ROUTING_KEY_VEHICULOS = "mineria.vehiculos.#";
    private static final String EXCHANGE_MQTT = "amq.topic"; 
    private static final String QUEUE_VEHICULOS = "cola.transitoria.posiciones"; 
    private static final String QUEUE_SEMAFOROS = "cola.persistente.semaforos"; 
    private static final String ROUTING_KEY_SEMEFOROS_ESTADO = "semaforos.#.estado";

    private static final String EXCHANGE_MINA = "mina.exchange.principal";
    private static final String QUEUE_ALERTAS = "cola.persistente.central.alertas";
    private static final String QUEUE_TOPOLOGIA = "cola.persistente.central.topologia";
    private static final String ROUTING_KEY_TOPOLOGIA = "semaforos.topologia.cambios";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        
        // ==========================================================
        // Notificaciones
        // ==========================================================
        channel.exchangeDeclare(EXCHANGE_MINA, BuiltinExchangeType.TOPIC, true);
        channel.queueDeclare(QUEUE_ALERTAS, true, false, false, null);
        channel.queueBind(QUEUE_ALERTAS, EXCHANGE_MINA, "#");
        DeliverCallback alertasCallback = (consumerTag, delivery) -> {
            try {
                String alertaJson = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [!!! ALERTA CRÍTICA EN CENTRAL !!!] " + alertaJson);

                // Confirmación manual (Al menos una vez)
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

            } catch (Exception e) {
                System.err.println(" Error procesando la alerta.");
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(), true);
            }
        };
        System.out.println(" [*] Escuchando Alertas de Notificaciones en el nuevo Exchange...");
        channel.basicConsume(QUEUE_ALERTAS, false, alertasCallback, consumerTag -> {
        });
        
        // ==========================================================
        // ESCUCHAR CAMBIOS DE TOPOLOGÍA (Pub/Sub)
        // ==========================================================
        channel.queueDeclare(QUEUE_SEMAFOROS, true, false, false, null);
        channel.queueBind(QUEUE_SEMAFOROS, EXCHANGE_MQTT, "semaforos.#.estado");

        DeliverCallback cambiosCallback = (consumerTag, delivery) -> {
            String cambioJson = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [¡ALERTA!] Topología actualizada en la mina: " + cambioJson);
            // actualizar mapa interno de la Estación
        };
        channel.basicConsume(QUEUE_SEMAFOROS, true, cambiosCallback, consumerTag -> { });

        // ==========================================================
        // vehículos
        // ==========================================================
        channel.queueDeclare(QUEUE_VEHICULOS, false, false, true, null);
        channel.queueBind(QUEUE_VEHICULOS, EXCHANGE_MQTT, "mineria.vehiculos.#");

        DeliverCallback vehiculosCallback = (consumerTag, delivery) -> {
            String mensaje = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [GPS] Vehículo: " + mensaje);
        };
        // autoAck: true
        channel.basicConsume(QUEUE_VEHICULOS, true, vehiculosCallback, consumerTag -> { });
        
        // ==========================================================
        // Cambios de posiciones de semaforos
        // ==========================================================
        channel.queueDeclare(QUEUE_TOPOLOGIA, true, false, false, null);

        channel.queueBind(QUEUE_TOPOLOGIA, EXCHANGE_MINA, ROUTING_KEY_TOPOLOGIA);

        channel.queuePurge(QUEUE_TOPOLOGIA);

        DeliverCallback topologiaCallback = (consumerTag, delivery) -> {
            try {
                String jsonCambio = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [MAPA MINA] Topología actualizada: " + jsonCambio);

                // actualizar mapa
                // 2. CONFIRMACIÓN MANUAL
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

            } catch (Exception e) {
                System.err.println(" Error procesando cambio de topología.");
                // 3. RECHAZO (Si falla, RabbitMQ lo vuelve a encolar)
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(), true);
            }
        };

        System.out.println(" [*] Escuchando cambios de topología con garantía At-Least-Once...");

        // 4. CONSUMO MANUAL (autoAck = false)
        channel.basicConsume(QUEUE_TOPOLOGIA, false, topologiaCallback, consumerTag -> {
        });

        // ==========================================================
        // INICIALIZACIÓN: PEDIR TOPOLOGÍA POR RPC A QUARKUS
        // ==========================================================
        System.out.println(" [*] Solicitando topología de semáforos por RPC...");
        String topologiaJson = pedirTopologiaRpc(channel);
        System.out.println(" [✓] Topología recibida: " + topologiaJson);
        System.out.println(" [*] Estación Central 100% operativa.");
        
        // Cambiar color semaforo
        Thread.sleep(5000);
        cambiarSemaforo(channel, "01", "CAMBIAR_A_VERDE");
    }

    // RPC de RabbitMQ en Java Clásico
    public static String pedirTopologiaRpc(Channel channel) throws Exception {
        String replyQueueName = channel.queueDeclare().getQueue();
        
        final String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", "rpc.semaforos.topologia", props, "GET_ALL".getBytes("UTF-8"));

        final CompletableFuture<String> response = new CompletableFuture<>();

        String cTag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.complete(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {});

        String result = response.get();
        channel.basicCancel(cTag);
        return result;
    }
    
    // Método reutilizable para enviar órdenes
    public static void cambiarSemaforo(Channel channel, String idSemaforo, String orden) {
        try {
            // Nota el punto '.' en lugar de '/' para la Routing Key en AMQP
            String routingKey = "semaforos." + idSemaforo + ".comandos";
            
            // PERSISTENT_TEXT_PLAIN asegura que el mensaje se guarde si el semáforo está desconectado
            channel.basicPublish(EXCHANGE_MQTT, routingKey, 
                    MessageProperties.PERSISTENT_TEXT_PLAIN, 
                    orden.getBytes("UTF-8"));
                    
            System.out.println(" [->] Orden enviada al Semáforo " + idSemaforo + ": " + orden);
        } catch (IOException e) {
            System.err.println("Error al enviar orden al semáforo.");
        }
    }
}