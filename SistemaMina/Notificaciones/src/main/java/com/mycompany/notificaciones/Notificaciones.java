package com.mycompany.notificaciones;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.mina.notificaciones.grpc.MutinyNotificacionesServiceGrpc;
import org.mina.notificaciones.grpc.NotificacionesProto;

import java.time.LocalDateTime;

@GrpcService
public class Notificaciones extends MutinyNotificacionesServiceGrpc.NotificacionesServiceImplBase {

    @Inject
    GestorAlertas gestor;

    @Override
    public Uni<NotificacionesProto.AlertaResponse> enviarAlerta(NotificacionesProto.AlertaRequest request) {
        
        System.out.println(" [gRPC] Petición entrante de Congestiones.");
        System.out.println("    -> Semáforo: " + request.getIdSemaforo() + " | Severidad: " + request.getNivelSeveridad());

        gestor.emitirAlertaCentral(request.getMensaje(), request.getNivelSeveridad(), request.getIdSemaforo());

        // Responder rápidamente a microservicio de Congestiones
        NotificacionesProto.AlertaResponse respuesta = NotificacionesProto.AlertaResponse.newBuilder()
                .setEstado("NOTIFICACION_PROCESADA")
                .setTimestamp(LocalDateTime.now().toString())
                .build();

        return Uni.createFrom().item(respuesta);
    }
}