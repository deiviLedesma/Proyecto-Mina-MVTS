package com.mycompany.congestiones;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.mina.notificaciones.grpc.MutinyNotificacionesServiceGrpc;
import org.mina.notificaciones.grpc.NotificacionesProto;
import org.mina.topologia.grpc.TopologiaProto;
import org.mina.topologia.grpc.TopologiaServiceGrpc;

@ApplicationScoped
public class Congestiones {

    @GrpcClient("cliente-notificaciones")
    MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub notificacionesAsyncClient;

    @GrpcClient("cliente-topologia")
    TopologiaServiceGrpc.TopologiaServiceBlockingStub topologiaClient;

    @Inject
    ObjectMapper mapper;

    @Channel("congestiones-out")
    Emitter<String> emisor;

    void onStart(@Observes StartupEvent ev) {
        System.out.println(" [*] Microservicio Congestiones inicializado.");
        analizarTraficoMina();
    }

    public void analizarTraficoMina() {
        System.out.println(" [*] Solicitando topologia a SemaforosTopologia via gRPC...");

        try {
            TopologiaProto.EmptyRequest request = TopologiaProto.EmptyRequest.newBuilder().build();
            TopologiaProto.SemaforoListResponse response = topologiaClient.getSemaforos(request);

            System.out.println(" [OK] Topologia recibida con " + response.getSemaforosCount() + " semaforos.");

            for (TopologiaProto.Semaforo semaforo : response.getSemaforosList()) {
                System.out.printf("     -> Semaforo [%s]: Sentido %s (Lat: %f, Lon: %f)%n",
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
    public CompletionStage<Void> recibirGpsVehiculo(Message<byte[]> mensaje) {
        try {
            String mensajeGps = new String(mensaje.getPayload(), StandardCharsets.UTF_8);
            JsonNode nodo = mapper.readTree(mensajeGps);
            String idVehiculo = nodo.path("id").asText("");

            System.out.println(" [GPS Congestiones] Vehiculo en movimiento: " + mensajeGps);

            if ("camion-01".equals(idVehiculo)) {
                enviarAlertaAsincrona("Congestion", "Alta", "01");
                reportarCongestion("Congestion detectada para " + idVehiculo);
            }

            return mensaje.ack();
        } catch (Exception e) {
            System.err.println(" [X] Error procesando GPS del vehiculo. Solicitando reintento...");
            e.printStackTrace();
            return mensaje.nack(e);
        }
    }

    private void enviarAlertaAsincrona(String mensaje, String severidad, String idSemaforo) {
        NotificacionesProto.AlertaRequest request = NotificacionesProto.AlertaRequest.newBuilder()
                .setMensaje(mensaje)
                .setNivelSeveridad(severidad)
                .setIdSemaforo(idSemaforo)
                .build();

        System.out.println(" [->] Disparando alerta gRPC asincrona...");

        notificacionesAsyncClient.enviarAlerta(request)
                .subscribe().with(
                        respuesta -> System.out.println(" [OK Async] Alerta enviada con exito. Estado: " + respuesta.getEstado()),
                        error -> System.err.println(" [X Async] Fallo el envio de la alerta: " + error.getMessage())
                );
    }

    public void reportarCongestion(String datosCongestion) {
        emisor.send(datosCongestion);
        System.out.println("[Enviado] Congestion reportada al exchange principal: " + datosCongestion);
    }
}
