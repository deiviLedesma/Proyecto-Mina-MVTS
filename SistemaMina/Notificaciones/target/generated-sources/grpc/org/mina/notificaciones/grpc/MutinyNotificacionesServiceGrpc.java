package org.mina.notificaciones.grpc;

import static org.mina.notificaciones.grpc.NotificacionesServiceGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: notificaciones.proto")
public final class MutinyNotificacionesServiceGrpc implements io.quarkus.grpc.MutinyGrpc {

    private MutinyNotificacionesServiceGrpc() {
    }

    public static MutinyNotificacionesServiceStub newMutinyStub(io.grpc.Channel channel) {
        return new MutinyNotificacionesServiceStub(channel);
    }

    public static class MutinyNotificacionesServiceStub extends io.grpc.stub.AbstractStub<MutinyNotificacionesServiceStub> implements io.quarkus.grpc.MutinyStub {

        private NotificacionesServiceGrpc.NotificacionesServiceStub delegateStub;

        private MutinyNotificacionesServiceStub(io.grpc.Channel channel) {
            super(channel);
            delegateStub = NotificacionesServiceGrpc.newStub(channel);
        }

        private MutinyNotificacionesServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
            delegateStub = NotificacionesServiceGrpc.newStub(channel).build(channel, callOptions);
        }

        @Override
        protected MutinyNotificacionesServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new MutinyNotificacionesServiceStub(channel, callOptions);
        }

        public io.smallrye.mutiny.Uni<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request) {
            return io.quarkus.grpc.stubs.ClientCalls.oneToOne(request, delegateStub::enviarAlerta);
        }
    }

    public static abstract class NotificacionesServiceImplBase implements io.grpc.BindableService {

        private String compression;

        /**
         * Set whether the server will try to use a compressed response.
         *
         * @param compression the compression, e.g {@code gzip}
         */
        public NotificacionesServiceImplBase withCompression(String compression) {
            this.compression = compression;
            return this;
        }

        public io.smallrye.mutiny.Uni<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override
        public io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(org.mina.notificaciones.grpc.NotificacionesServiceGrpc.getEnviarAlertaMethod(), asyncUnaryCall(new MethodHandlers<org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest, org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse>(this, METHODID_ENVIAR_ALERTA, compression))).build();
        }
    }

    private static final int METHODID_ENVIAR_ALERTA = 0;

    private static final class MethodHandlers<Req, Resp> implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>, io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {

        private final NotificacionesServiceImplBase serviceImpl;

        private final int methodId;

        private final String compression;

        MethodHandlers(NotificacionesServiceImplBase serviceImpl, int methodId, String compression) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
            this.compression = compression;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                case METHODID_ENVIAR_ALERTA:
                    io.quarkus.grpc.stubs.ServerCalls.oneToOne((org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest) request, (io.grpc.stub.StreamObserver<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse>) responseObserver, compression, serviceImpl::enviarAlerta);
                    break;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }
}
