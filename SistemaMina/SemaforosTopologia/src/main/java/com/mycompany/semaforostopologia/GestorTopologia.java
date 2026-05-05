package com.mycompany.semaforostopologia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class GestorTopologia {

    // 1. (En memoria por ahora)
    private Map<String, SemaforoDto> dbSemaforos = new HashMap<>();

    @Inject
    ObjectMapper mapper;

    @Inject
    @Channel("emisor-cambios")
    Emitter<String> cambiosEmitter;

    void onStart(@Observes StartupEvent ev) {
        dbSemaforos.put("01", new SemaforoDto("01", 27.481, -109.931, "NORTE"));
        dbSemaforos.put("02", new SemaforoDto("02", 27.482, -109.932, "ESTE"));
        
        iniciarServidorRpcRabbitMQ();
    }
    
    public Collection<SemaforoDto> obtenerTodosLosSemaforos() {
        return dbSemaforos.values();
    }

    private void iniciarServidorRpcRabbitMQ() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            
            Connection connection = factory.newConnection();
            com.rabbitmq.client.Channel channel = connection.createChannel();
            
            String rpcQueueName = "rpc.semaforos.topologia";
            channel.queueDeclare(rpcQueueName, true, false, false, null);
            channel.queuePurge(rpcQueueName);

            System.out.println(" [RPC] Servidor RabbitMQ RPC esperando peticiones...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String respuestaJson = mapper.writeValueAsString(dbSemaforos.values());

                channel.basicPublish("", delivery.getProperties().getReplyTo(), 
                                     replyProps, respuestaJson.getBytes("UTF-8"));
                
                // Confirmar la proseción
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                
                actualizarSemaforo("01", 2, 2, "SUR");
            };

            channel.basicConsume(rpcQueueName, false, deliverCallback, (consumerTag -> {}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarSemaforo(String id, double lat, double lon, String sentido) {
        SemaforoDto actualizado = new SemaforoDto(id, lat, lon, sentido);
        dbSemaforos.put(id, actualizado);
        
        try {
            String json = mapper.writeValueAsString(actualizado);
            // Publicar a RabbitMQ
            cambiosEmitter.send(json);
            System.out.println(" [EVENTO] Cambio emitido: " + json);
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Clase DTO de apoyo
    public static class SemaforoDto {
        public String id; public double lat; public double lon; public String sentido;
        public SemaforoDto(String id, double lat, double lon, String sentido) {
            this.id = id; this.lat = lat; this.lon = lon; this.sentido = sentido;
        }
    }
}