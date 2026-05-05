package org.mina.notificaciones.grpc;

import io.grpc.BindableService;
import io.quarkus.grpc.GrpcService;
import io.quarkus.grpc.MutinyBean;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: notificaciones.proto")
public class NotificacionesServiceBean extends MutinyNotificacionesServiceGrpc.NotificacionesServiceImplBase implements BindableService, MutinyBean {

    private final NotificacionesService delegate;

    NotificacionesServiceBean(@GrpcService NotificacionesService delegate) {
        this.delegate = delegate;
    }

    @Override
    public io.smallrye.mutiny.Uni<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request) {
        try {
            return delegate.enviarAlerta(request);
        } catch (UnsupportedOperationException e) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }
    }
}
