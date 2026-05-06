package com.mycompany.congestiones;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.runtime.StartupEvent; 
import jakarta.enterprise.event.Observes;
import org.mina.notificaciones.grpc.MutinyNotificacionesServiceGrpc;
import org.mina.notificaciones.grpc.NotificacionesProto;
import org.mina.topologia.grpc.TopologiaProto;
import org.mina.topologia.grpc.TopologiaServiceGrpc;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class Congestiones {

    @GrpcClient("cliente-notificaciones")
    MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub notificacionesAsyncClient;
    
    @GrpcClient("cliente-topologia")
    TopologiaServiceGrpc.TopologiaServiceBlockingStub topologiaClient;

    @Inject
    ObjectMapper mapper;
    
    private final Instant tiempoDeArranque = Instant.now();
    
    @Incoming("cambios-semaforos-in")
    public CompletionStage<Void> recibirActualizacionSemaforo(Message<String> mensaje) {
        
        try {
            String jsonPayload = mensaje.getPayload();
            JsonNode nodo = mapper.readTree(jsonPayload);
            
            if (nodo.has("timestamp")) {
                long timestampMensaje = nodo.get("timestamp").asLong();
                Instant instanteMensaje = Instant.ofEpochMilli(timestampMensaje);
                
                if (instanteMensaje.isBefore(tiempoDeArranque)) {
                    System.out.println(" [!] Mensaje obsoleto ignorado (ya cubierto por gRPC).");
                    return mensaje.ack(); 
                }
            }

            System.out.println(" [MAPA MINA] Actualizando semáforo en tiempo real: " + jsonPayload);

            return mensaje.ack();

        } catch (Exception e) {
            System.err.println(" [X] Error procesando cambio. Solicitando reintento...");
            e.printStackTrace();
            
            return mensaje.nack(e);
        }
    }
    
    void onStart(@Observes StartupEvent ev) {
        System.out.println(" [*] Microservicio Congestiones inicializado.");
        analizarTraficoMina(); 
    }

    public void analizarTraficoMina() {
        System.out.println(" [*] Solicitando topología a SemaforosTopologia vía gRPC...");

        try {
            TopologiaProto.EmptyRequest request = TopologiaProto.EmptyRequest.newBuilder().build();
            TopologiaProto.SemaforoListResponse response = topologiaClient.getSemaforos(request);

            System.out.println(" [✓] Topología recibida con " + response.getSemaforosCount() + " semáforos.");
            
            for (TopologiaProto.Semaforo semaforo : response.getSemaforosList()) {
                System.out.printf("     -> Semáforo [%s]: Sentido %s (Lat: %f, Lon: %f)%n",
                        semaforo.getId(), 
                        semaforo.getSentido(), 
                        semaforo.getLatitud(), 
                        semaforo.getLongitud());
            }
        } catch (Exception e) {
            System.err.println(" [X] Error al contactar al servidor gRPC: " + e.getMessage());
        }
    }
    
    @Incoming("vehiculos-in")
    public void recibirGpsVehiculo(byte[] bytes) {
        String mensajeGps = new String(bytes);
        
        System.out.println(" [GPS Congestiones] Vehículo en movimiento: " + mensajeGps);
        if (mensajeGps.contains("1")) {
            enviarAlertaAsincrona("Congestion", "Alta", "1");
            reportarCongestion("congestiona a guardar");
        }
        
        // Lista de Posiciones
    }
    
    private void enviarAlertaAsincrona(String mensaje, String severidad, String idSemaforo) {
        
        NotificacionesProto.AlertaRequest request = NotificacionesProto.AlertaRequest.newBuilder()
                .setMensaje(mensaje)
                .setNivelSeveridad(severidad)
                .setIdSemaforo(idSemaforo)
                .build();

        System.out.println(" [->] Disparando alerta gRPC asíncrona...");

        // cliente Mutiny. 
        // maneja la respuesta en segundo plano cuando llegue.
        notificacionesAsyncClient.enviarAlerta(request)
                .subscribe().with(
                        respuesta -> {
                            System.out.println(" [✓ Async] Alerta enviada con éxito. Estado: " + respuesta.getEstado());
                        },
                        error -> {
                            System.err.println(" [X Async] Falló el envío de la alerta: " + error.getMessage());
                        }
                );
    }

    // puente con el application.properties
    @Channel("congestiones-out")
    Emitter<String> emisor;
    public void reportarCongestion(String datosCongestion) {

        // Enviamos el mensaje en texto plano
        emisor.send(datosCongestion);

        System.out.println("[Enviado] Congestión reportada al exchange principal: " + datosCongestion);
    }
}
