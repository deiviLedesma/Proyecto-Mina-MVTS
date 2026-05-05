package org.mina.notificaciones.grpc;

import io.quarkus.grpc.MutinyService;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: notificaciones.proto")
public interface NotificacionesService extends MutinyService {

    io.smallrye.mutiny.Uni<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request);
}
