package org.mina.topologia.grpc;

import io.quarkus.grpc.MutinyService;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: topologia.proto")
public interface TopologiaService extends MutinyService {

    io.smallrye.mutiny.Uni<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request);
}
