package org.mina.notificaciones.grpc;

import java.util.function.BiFunction;
import io.quarkus.grpc.MutinyClient;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: notificaciones.proto")
public class NotificacionesServiceClient implements NotificacionesService, MutinyClient<MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub> {

    private final MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub stub;

    public NotificacionesServiceClient(String name, io.grpc.Channel channel, BiFunction<String, MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub, MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub> stubConfigurator) {
        this.stub = stubConfigurator.apply(name, MutinyNotificacionesServiceGrpc.newMutinyStub(channel));
    }

    private NotificacionesServiceClient(MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub stub) {
        this.stub = stub;
    }

    public NotificacionesServiceClient newInstanceWithStub(MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub stub) {
        return new NotificacionesServiceClient(stub);
    }

    @Override
    public MutinyNotificacionesServiceGrpc.MutinyNotificacionesServiceStub getStub() {
        return stub;
    }

    @Override
    public io.smallrye.mutiny.Uni<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request) {
        return stub.enviarAlerta(request);
    }
}
