package org.mina.topologia.grpc;

import static org.mina.topologia.grpc.TopologiaServiceGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: topologia.proto")
public final class MutinyTopologiaServiceGrpc implements io.quarkus.grpc.MutinyGrpc {

    private MutinyTopologiaServiceGrpc() {
    }

    public static MutinyTopologiaServiceStub newMutinyStub(io.grpc.Channel channel) {
        return new MutinyTopologiaServiceStub(channel);
    }

    public static class MutinyTopologiaServiceStub extends io.grpc.stub.AbstractStub<MutinyTopologiaServiceStub> implements io.quarkus.grpc.MutinyStub {

        private TopologiaServiceGrpc.TopologiaServiceStub delegateStub;

        private MutinyTopologiaServiceStub(io.grpc.Channel channel) {
            super(channel);
            delegateStub = TopologiaServiceGrpc.newStub(channel);
        }

        private MutinyTopologiaServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
            delegateStub = TopologiaServiceGrpc.newStub(channel).build(channel, callOptions);
        }

        @Override
        protected MutinyTopologiaServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new MutinyTopologiaServiceStub(channel, callOptions);
        }

        public io.smallrye.mutiny.Uni<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request) {
            return io.quarkus.grpc.stubs.ClientCalls.oneToOne(request, delegateStub::getSemaforos);
        }
    }

    public static abstract class TopologiaServiceImplBase implements io.grpc.BindableService {

        private String compression;

        /**
         * Set whether the server will try to use a compressed response.
         *
         * @param compression the compression, e.g {@code gzip}
         */
        public TopologiaServiceImplBase withCompression(String compression) {
            this.compression = compression;
            return this;
        }

        public io.smallrye.mutiny.Uni<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override
        public io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(org.mina.topologia.grpc.TopologiaServiceGrpc.getGetSemaforosMethod(), asyncUnaryCall(new MethodHandlers<org.mina.topologia.grpc.TopologiaProto.EmptyRequest, org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse>(this, METHODID_GET_SEMAFOROS, compression))).build();
        }
    }

    private static final int METHODID_GET_SEMAFOROS = 0;

    private static final class MethodHandlers<Req, Resp> implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>, io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {

        private final TopologiaServiceImplBase serviceImpl;

        private final int methodId;

        private final String compression;

        MethodHandlers(TopologiaServiceImplBase serviceImpl, int methodId, String compression) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
            this.compression = compression;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                case METHODID_GET_SEMAFOROS:
                    io.quarkus.grpc.stubs.ServerCalls.oneToOne((org.mina.topologia.grpc.TopologiaProto.EmptyRequest) request, (io.grpc.stub.StreamObserver<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse>) responseObserver, compression, serviceImpl::getSemaforos);
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
