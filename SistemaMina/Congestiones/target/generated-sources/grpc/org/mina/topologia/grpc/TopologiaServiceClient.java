package org.mina.topologia.grpc;

import java.util.function.BiFunction;
import io.quarkus.grpc.MutinyClient;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: topologia.proto")
public class TopologiaServiceClient implements TopologiaService, MutinyClient<MutinyTopologiaServiceGrpc.MutinyTopologiaServiceStub> {

    private final MutinyTopologiaServiceGrpc.MutinyTopologiaServiceStub stub;

    public TopologiaServiceClient(String name, io.grpc.Channel channel, BiFunction<String, MutinyTopologiaServiceGrpc.MutinyTopologiaServiceStub, MutinyTopologiaServiceGrpc.MutinyTopologiaServiceStub> stubConfigurator) {
        this.stub = stubConfigurator.apply(name, MutinyTopologiaServiceGrpc.newMutinyStub(channel));
    }

    private TopologiaServiceClient(MutinyTopologiaServiceGrpc.MutinyTopologiaServiceStub stub) {
        this.stub = stub;
    }

    public TopologiaServiceClient newInstanceWithStub(MutinyTopologiaServiceGrpc.MutinyTopologiaServiceStub stub) {
        return new TopologiaServiceClient(stub);
    }

    @Override
    public MutinyTopologiaServiceGrpc.MutinyTopologiaServiceStub getStub() {
        return stub;
    }

    @Override
    public io.smallrye.mutiny.Uni<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request) {
        return stub.getSemaforos(request);
    }
}
