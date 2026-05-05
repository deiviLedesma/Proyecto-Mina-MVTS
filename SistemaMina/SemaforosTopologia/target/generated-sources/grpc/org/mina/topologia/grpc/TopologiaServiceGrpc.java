package org.mina.topologia.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.quarkus.Generated(value = "by gRPC proto compiler (version 1.62.2)", comments = "Source: topologia.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TopologiaServiceGrpc {

    private TopologiaServiceGrpc() {
    }

    public static final java.lang.String SERVICE_NAME = "TopologiaService";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<org.mina.topologia.grpc.TopologiaProto.EmptyRequest, org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getGetSemaforosMethod;

    @io.grpc.stub.annotations.RpcMethod(fullMethodName = SERVICE_NAME + '/' + "GetSemaforos", requestType = org.mina.topologia.grpc.TopologiaProto.EmptyRequest.class, responseType = org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse.class, methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<org.mina.topologia.grpc.TopologiaProto.EmptyRequest, org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getGetSemaforosMethod() {
        io.grpc.MethodDescriptor<org.mina.topologia.grpc.TopologiaProto.EmptyRequest, org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getGetSemaforosMethod;
        if ((getGetSemaforosMethod = TopologiaServiceGrpc.getGetSemaforosMethod) == null) {
            synchronized (TopologiaServiceGrpc.class) {
                if ((getGetSemaforosMethod = TopologiaServiceGrpc.getGetSemaforosMethod) == null) {
                    TopologiaServiceGrpc.getGetSemaforosMethod = getGetSemaforosMethod = io.grpc.MethodDescriptor.<org.mina.topologia.grpc.TopologiaProto.EmptyRequest, org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse>newBuilder().setType(io.grpc.MethodDescriptor.MethodType.UNARY).setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSemaforos")).setSampledToLocalTracing(true).setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(org.mina.topologia.grpc.TopologiaProto.EmptyRequest.getDefaultInstance())).setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse.getDefaultInstance())).setSchemaDescriptor(new TopologiaServiceMethodDescriptorSupplier("GetSemaforos")).build();
                }
            }
        }
        return getGetSemaforosMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static TopologiaServiceStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<TopologiaServiceStub> factory = new io.grpc.stub.AbstractStub.StubFactory<TopologiaServiceStub>() {

            @java.lang.Override
            public TopologiaServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new TopologiaServiceStub(channel, callOptions);
            }
        };
        return TopologiaServiceStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static TopologiaServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<TopologiaServiceBlockingStub> factory = new io.grpc.stub.AbstractStub.StubFactory<TopologiaServiceBlockingStub>() {

            @java.lang.Override
            public TopologiaServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new TopologiaServiceBlockingStub(channel, callOptions);
            }
        };
        return TopologiaServiceBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static TopologiaServiceFutureStub newFutureStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<TopologiaServiceFutureStub> factory = new io.grpc.stub.AbstractStub.StubFactory<TopologiaServiceFutureStub>() {

            @java.lang.Override
            public TopologiaServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new TopologiaServiceFutureStub(channel, callOptions);
            }
        };
        return TopologiaServiceFutureStub.newStub(factory, channel);
    }

    /**
     */
    public interface AsyncService {

        /**
         */
        default void getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request, io.grpc.stub.StreamObserver<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSemaforosMethod(), responseObserver);
        }
    }

    /**
     * Base class for the server implementation of the service TopologiaService.
     */
    public static abstract class TopologiaServiceImplBase implements io.grpc.BindableService, AsyncService {

        @java.lang.Override
        public io.grpc.ServerServiceDefinition bindService() {
            return TopologiaServiceGrpc.bindService(this);
        }
    }

    /**
     * A stub to allow clients to do asynchronous rpc calls to service TopologiaService.
     */
    public static class TopologiaServiceStub extends io.grpc.stub.AbstractAsyncStub<TopologiaServiceStub> {

        private TopologiaServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected TopologiaServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new TopologiaServiceStub(channel, callOptions);
        }

        /**
         */
        public void getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request, io.grpc.stub.StreamObserver<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(getChannel().newCall(getGetSemaforosMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     * A stub to allow clients to do synchronous rpc calls to service TopologiaService.
     */
    public static class TopologiaServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<TopologiaServiceBlockingStub> {

        private TopologiaServiceBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected TopologiaServiceBlockingStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new TopologiaServiceBlockingStub(channel, callOptions);
        }

        /**
         */
        public org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(getChannel(), getGetSemaforosMethod(), getCallOptions(), request);
        }
    }

    /**
     * A stub to allow clients to do ListenableFuture-style rpc calls to service TopologiaService.
     */
    public static class TopologiaServiceFutureStub extends io.grpc.stub.AbstractFutureStub<TopologiaServiceFutureStub> {

        private TopologiaServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected TopologiaServiceFutureStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new TopologiaServiceFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(getChannel().newCall(getGetSemaforosMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_GET_SEMAFOROS = 0;

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
                case METHODID_GET_SEMAFOROS:
                    serviceImpl.getSemaforos((org.mina.topologia.grpc.TopologiaProto.EmptyRequest) request, (io.grpc.stub.StreamObserver<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse>) responseObserver);
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
        return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(getGetSemaforosMethod(), io.grpc.stub.ServerCalls.asyncUnaryCall(new MethodHandlers<org.mina.topologia.grpc.TopologiaProto.EmptyRequest, org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse>(service, METHODID_GET_SEMAFOROS))).build();
    }

    private static abstract class TopologiaServiceBaseDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {

        TopologiaServiceBaseDescriptorSupplier() {
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return org.mina.topologia.grpc.TopologiaProto.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("TopologiaService");
        }
    }

    private static final class TopologiaServiceFileDescriptorSupplier extends TopologiaServiceBaseDescriptorSupplier {

        TopologiaServiceFileDescriptorSupplier() {
        }
    }

    private static final class TopologiaServiceMethodDescriptorSupplier extends TopologiaServiceBaseDescriptorSupplier implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {

        private final java.lang.String methodName;

        TopologiaServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
            synchronized (TopologiaServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new TopologiaServiceFileDescriptorSupplier()).addMethod(getGetSemaforosMethod()).build();
                }
            }
        }
        return result;
    }
}
