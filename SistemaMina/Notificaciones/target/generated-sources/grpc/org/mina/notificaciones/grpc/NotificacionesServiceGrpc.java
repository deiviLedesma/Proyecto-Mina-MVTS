package org.mina.notificaciones.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.quarkus.Generated(value = "by gRPC proto compiler (version 1.62.2)", comments = "Source: notificaciones.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NotificacionesServiceGrpc {

    private NotificacionesServiceGrpc() {
    }

    public static final java.lang.String SERVICE_NAME = "NotificacionesService";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest, org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> getEnviarAlertaMethod;

    @io.grpc.stub.annotations.RpcMethod(fullMethodName = SERVICE_NAME + '/' + "EnviarAlerta", requestType = org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest.class, responseType = org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse.class, methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest, org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> getEnviarAlertaMethod() {
        io.grpc.MethodDescriptor<org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest, org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> getEnviarAlertaMethod;
        if ((getEnviarAlertaMethod = NotificacionesServiceGrpc.getEnviarAlertaMethod) == null) {
            synchronized (NotificacionesServiceGrpc.class) {
                if ((getEnviarAlertaMethod = NotificacionesServiceGrpc.getEnviarAlertaMethod) == null) {
                    NotificacionesServiceGrpc.getEnviarAlertaMethod = getEnviarAlertaMethod = io.grpc.MethodDescriptor.<org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest, org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse>newBuilder().setType(io.grpc.MethodDescriptor.MethodType.UNARY).setFullMethodName(generateFullMethodName(SERVICE_NAME, "EnviarAlerta")).setSampledToLocalTracing(true).setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest.getDefaultInstance())).setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse.getDefaultInstance())).setSchemaDescriptor(new NotificacionesServiceMethodDescriptorSupplier("EnviarAlerta")).build();
                }
            }
        }
        return getEnviarAlertaMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static NotificacionesServiceStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<NotificacionesServiceStub> factory = new io.grpc.stub.AbstractStub.StubFactory<NotificacionesServiceStub>() {

            @java.lang.Override
            public NotificacionesServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new NotificacionesServiceStub(channel, callOptions);
            }
        };
        return NotificacionesServiceStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static NotificacionesServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<NotificacionesServiceBlockingStub> factory = new io.grpc.stub.AbstractStub.StubFactory<NotificacionesServiceBlockingStub>() {

            @java.lang.Override
            public NotificacionesServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new NotificacionesServiceBlockingStub(channel, callOptions);
            }
        };
        return NotificacionesServiceBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static NotificacionesServiceFutureStub newFutureStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<NotificacionesServiceFutureStub> factory = new io.grpc.stub.AbstractStub.StubFactory<NotificacionesServiceFutureStub>() {

            @java.lang.Override
            public NotificacionesServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new NotificacionesServiceFutureStub(channel, callOptions);
            }
        };
        return NotificacionesServiceFutureStub.newStub(factory, channel);
    }

    /**
     */
    public interface AsyncService {

        /**
         */
        default void enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request, io.grpc.stub.StreamObserver<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnviarAlertaMethod(), responseObserver);
        }
    }

    /**
     * Base class for the server implementation of the service NotificacionesService.
     */
    public static abstract class NotificacionesServiceImplBase implements io.grpc.BindableService, AsyncService {

        @java.lang.Override
        public io.grpc.ServerServiceDefinition bindService() {
            return NotificacionesServiceGrpc.bindService(this);
        }
    }

    /**
     * A stub to allow clients to do asynchronous rpc calls to service NotificacionesService.
     */
    public static class NotificacionesServiceStub extends io.grpc.stub.AbstractAsyncStub<NotificacionesServiceStub> {

        private NotificacionesServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected NotificacionesServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new NotificacionesServiceStub(channel, callOptions);
        }

        /**
         */
        public void enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request, io.grpc.stub.StreamObserver<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(getChannel().newCall(getEnviarAlertaMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     * A stub to allow clients to do synchronous rpc calls to service NotificacionesService.
     */
    public static class NotificacionesServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<NotificacionesServiceBlockingStub> {

        private NotificacionesServiceBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected NotificacionesServiceBlockingStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new NotificacionesServiceBlockingStub(channel, callOptions);
        }

        /**
         */
        public org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(getChannel(), getEnviarAlertaMethod(), getCallOptions(), request);
        }
    }

    /**
     * A stub to allow clients to do ListenableFuture-style rpc calls to service NotificacionesService.
     */
    public static class NotificacionesServiceFutureStub extends io.grpc.stub.AbstractFutureStub<NotificacionesServiceFutureStub> {

        private NotificacionesServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected NotificacionesServiceFutureStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new NotificacionesServiceFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse> enviarAlerta(org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(getChannel().newCall(getEnviarAlertaMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_ENVIAR_ALERTA = 0;

    private static final class MethodHandlers<Req, Resp> implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>, io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {

        private final AsyncService serviceImpl;

        private final int methodId;

        MethodHandlers(AsyncService serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                case METHODID_ENVIAR_ALERTA:
                    serviceImpl.enviarAlerta((org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest) request, (io.grpc.stub.StreamObserver<org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    public static io.grpc.ServerServiceDefinition bindService(AsyncService service) {
        return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(getEnviarAlertaMethod(), io.grpc.stub.ServerCalls.asyncUnaryCall(new MethodHandlers<org.mina.notificaciones.grpc.NotificacionesProto.AlertaRequest, org.mina.notificaciones.grpc.NotificacionesProto.AlertaResponse>(service, METHODID_ENVIAR_ALERTA))).build();
    }

    private static abstract class NotificacionesServiceBaseDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {

        NotificacionesServiceBaseDescriptorSupplier() {
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return org.mina.notificaciones.grpc.NotificacionesProto.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("NotificacionesService");
        }
    }

    private static final class NotificacionesServiceFileDescriptorSupplier extends NotificacionesServiceBaseDescriptorSupplier {

        NotificacionesServiceFileDescriptorSupplier() {
        }
    }

    private static final class NotificacionesServiceMethodDescriptorSupplier extends NotificacionesServiceBaseDescriptorSupplier implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {

        private final java.lang.String methodName;

        NotificacionesServiceMethodDescriptorSupplier(java.lang.String methodName) {
            this.methodName = methodName;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (NotificacionesServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new NotificacionesServiceFileDescriptorSupplier()).addMethod(getEnviarAlertaMethod()).build();
                }
            }
        }
        return result;
    }
}
